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

public class JudgeLic13Test {
    @Test
    public void testJudgeLic13Negative() {
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic13Data[] dataArray = getLic13Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic13Data data : dataArray) {
            boolean result = licJudge.judgeLic13(data.coordinates, data.aPoints, data.bPoints, data.radius1,
                    data.radius2);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic13Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic13Data[] dataArray = getLic13Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic13Data data : dataArray) {
            boolean result = licJudge.judgeLic13(data.coordinates, data.aPoints, data.bPoints, data.radius1,
                    data.radius2);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic13Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic13Data[] dataArray = getLic13Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic13Data data : dataArray) {
            boolean result = licJudge.judgeLic13(data.coordinates, data.aPoints, data.bPoints, data.radius1,
                    data.radius2);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private final int LIC_INDEX = 13;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    private record Lic13Data(Coordinate[] coordinates, int aPoints, int bPoints, double radius1, double radius2,
            boolean expectedResult, String errorMessage) {
    }

    private static Lic13Data[] getLic13Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic13Data> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            int aPoints = node.get("aPoints").asInt();
            int bPoints = node.get("bPoints").asInt();
            double radius1 = node.get("radius1").asDouble();
            double radius2 = node.get("radius2").asDouble();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic13Data(coordinates, aPoints, bPoints, radius1, radius2, expectedResult, errorMessage));
        }

        Lic13Data[] dataArray = new Lic13Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}