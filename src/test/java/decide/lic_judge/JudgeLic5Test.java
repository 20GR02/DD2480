package decide.lic_judge;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import decide.core.LicJudge;
import decide.model.Coordinate;
import decide.util.JsonFileReader;
import decide.util.JsonUtil;

public class JudgeLic5Test {

    private final int LIC_INDEX = 5;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    @Test
    public void testJudgeLic5Negative() {

        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic5Data[] dataArray = getLic5Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic5Data data : dataArray) {
            boolean result = licJudge.judgeLic5(data.coordinates);

            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic5Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic5Data[] dataArray = getLic5Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic5Data data : dataArray) {
            boolean result = licJudge.judgeLic5(data.coordinates);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic5Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic5Data[] dataArray = getLic5Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic5Data data : dataArray) {
            boolean result = licJudge.judgeLic5(data.coordinates);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private record Lic5Data(Coordinate[] coordinates,
            boolean expectedResult, String errorMessage) {
    }

    private static Lic5Data[] getLic5Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic5Data> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic5Data(coordinates, expectedResult, errorMessage));
        }

        Lic5Data[] dataArray = new Lic5Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}