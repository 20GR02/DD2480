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

public class JudgeLic7Test {
    @Test
    public void testJudgeLic7Negative() {
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic7Data[] dataArray = getLic7Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic7Data data : dataArray) {
            boolean result = licJudge.judgeLic7(data.coordinates, data.kPoints, data.length1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic7Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic7Data[] dataArray = getLic7Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic7Data data : dataArray) {
            boolean result = licJudge.judgeLic7(data.coordinates, data.kPoints, data.length1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic7Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic7Data[] dataArray = getLic7Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic7Data data : dataArray) {
            boolean result = licJudge.judgeLic7(data.coordinates, data.kPoints, data.length1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private final int LIC_INDEX = 7;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    private record Lic7Data(Coordinate[] coordinates, int kPoints, double length1, boolean expectedResult,
            String errorMessage) {
    }

    private static Lic7Data[] getLic7Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic7Data> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            int kPoints = node.get("kPoints").asInt();
            double length1 = node.get("length1").asDouble();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic7Data(coordinates, kPoints, length1, expectedResult, errorMessage));
        }

        Lic7Data[] dataArray = new Lic7Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}
