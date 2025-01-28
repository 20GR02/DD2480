package decide.lic_judge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import decide.core.LicJudge;
import decide.model.Coordinate;
import decide.util.JsonFileReader;
import decide.util.JsonUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JudgeLic9Test {
    @Test
    public void testJudgeLic9Negative() {
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic9Data[] dataArray = getLic9Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic9Data data : dataArray) {
            boolean result = licJudge.judgeLic9(data.coordinates, data.cPoints, data.dPoints, data.epsilon);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic9Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic9Data[] dataArray = getLic9Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic9Data data : dataArray) {
            boolean result = licJudge.judgeLic9(data.coordinates, data.cPoints, data.dPoints, data.epsilon);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic9Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic9Data[] dataArray = getLic9Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic9Data data : dataArray) {
            boolean result = licJudge.judgeLic9(data.coordinates, data.cPoints, data.dPoints, data.epsilon);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private final int LIC_INDEX = 9;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    private record Lic9Data(Coordinate[] coordinates, int cPoints, int dPoints, double epsilon,
            boolean expectedResult, String errorMessage) {
    }

    private static Lic9Data[] getLic9Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic9Data> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            int cPoints = node.get("cPoints").asInt();
            int dPoints = node.get("dPoints").asInt();
            double epsilon = node.get("epsilon").asDouble();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic9Data(coordinates, cPoints, dPoints, epsilon, expectedResult, errorMessage));
        }

        Lic9Data[] dataArray = new Lic9Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}