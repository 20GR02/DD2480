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

public class JudgeLic8Test {
    @Test
    public void testJudgeLic8Negative() {
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic8Data[] dataArray = getLic8Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic8Data data : dataArray) {
            boolean result = licJudge.judgeLic8(data.coordinates, data.aPoints, data.bPoints, data.radius1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic8Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic8Data[] dataArray = getLic8Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic8Data data : dataArray) {
            boolean result = licJudge.judgeLic8(data.coordinates, data.aPoints, data.bPoints, data.radius1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic8Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic8Data[] dataArray = getLic8Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic8Data data : dataArray) {
            boolean result = licJudge.judgeLic8(data.coordinates, data.aPoints, data.bPoints, data.radius1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private final int LIC_INDEX = 8;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    private record Lic8Data(Coordinate[] coordinates, int aPoints, int bPoints, double radius1,
                            boolean expectedResult, String errorMessage) {
    }

    private static Lic8Data[] getLic8Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic8Data> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            int aPoints = node.get("aPoints").asInt();
            int bPoints = node.get("bPoints").asInt();
            double radius1 = node.get("radius1").asDouble();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic8Data(coordinates, aPoints, bPoints, radius1, expectedResult, errorMessage));
        }

        Lic8Data[] dataArray = new Lic8Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}