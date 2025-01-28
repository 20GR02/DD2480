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

public class JudgeLic2Test {
    @Test
    public void testJudgeLic2Negative() {
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic2Data[] dataArray = getLic2Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic2Data data : dataArray) {
            boolean result = licJudge.judgeLic2(data.coordinates, data.radius1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic2Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic2Data[] dataArray = getLic2Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic2Data data : dataArray) {
            boolean result = licJudge.judgeLic2(data.coordinates, data.radius1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic2Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic2Data[] dataArray = getLic2Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic2Data data : dataArray) {
            boolean result = licJudge.judgeLic2(data.coordinates, data.radius1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private final int LIC_INDEX = 2;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    private record Lic2Data(Coordinate[] coordinates, double radius1
            , boolean expectedResult, String errorMessage) {
    }

    private static Lic2Data[] getLic2Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic2Data> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            double epsilon = node.get("epsilon").asDouble();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic2Data(coordinates, epsilon, expectedResult, errorMessage));
        }

        Lic2Data[] dataArray = new Lic2Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}
