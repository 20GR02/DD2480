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

public class JudgeLic12Test {
    @Test
    public void testJudgeLic12Negative() {
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic12Data[] dataArray = getLic12Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic12Data data : dataArray) {
            boolean result = licJudge.judgeLic12(data.coordinates, data.length1, data.length2, data.kPoints);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic12Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic12Data[] dataArray = getLic12Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic12Data data : dataArray) {
            boolean result = licJudge.judgeLic12(data.coordinates, data.length1, data.length2, data.kPoints);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic12Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic12Data[] dataArray = getLic12Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic12Data data : dataArray) {
            boolean result = licJudge.judgeLic12(data.coordinates, data.length1, data.length2, data.kPoints);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private final int LIC_INDEX = 12;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    private record Lic12Data(Coordinate[] coordinates, double length1, double length2, int kPoints,
            boolean expectedResult, String errorMessage) {
    }

    private static Lic12Data[] getLic12Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic12Data> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            double length1 = node.get("length1").asDouble();
            double length2 = node.get("length2").asDouble();
            int kPoints = node.get("kPoints").asInt();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic12Data(coordinates, length1, length2, kPoints, expectedResult, errorMessage));
        }

        Lic12Data[] dataArray = new Lic12Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}