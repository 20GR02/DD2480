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

public class JudgeLic1Test {
    @Test
    public void testJudgeLic1Negative() {
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic1Data[] dataArray = getLic1Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic1Data data : dataArray) {
            boolean result = licJudge.judgeLic1(data.coordinates, data.radius1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic1Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic1Data[] dataArray = getLic1Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic1Data data : dataArray) {
            boolean result = licJudge.judgeLic1(data.coordinates, data.radius1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic1Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic1Data[] dataArray = getLic1Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic1Data data : dataArray) {
            boolean result = licJudge.judgeLic1(data.coordinates, data.radius1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private final int LIC_INDEX = 1;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    private record Lic1Data(Coordinate[] coordinates, double radius1
            , boolean expectedResult, String errorMessage) {
    }

    private static Lic1Data[] getLic1Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic1Data> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            double radius1 = node.get("radius1").asDouble();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic1Data(coordinates, radius1, expectedResult, errorMessage));
        }

        Lic1Data[] dataArray = new Lic1Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}
