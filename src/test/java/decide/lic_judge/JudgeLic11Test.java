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

public class JudgeLic11Test {

    private final int LIC_INDEX = 11;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    @Test
    public void testJudgeLic11Negative() {
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic11Data[] dataArray = getLic11Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic11Data data : dataArray) {

            boolean result = licJudge.judgeLic11(data.coordinates, data.gPoints);

            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic11Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic11Data[] dataArray = getLic11Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic11Data data : dataArray) {
            boolean result = licJudge.judgeLic11(data.coordinates, data.gPoints);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic11Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic11Data[] dataArray = getLic11Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic11Data data : dataArray) {
            boolean result = licJudge.judgeLic11(data.coordinates, data.gPoints);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private record Lic11Data(Coordinate[] coordinates, int gPoints,
            boolean expectedResult, String errorMessage) {
    }

    private static Lic11Data[] getLic11Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic11Data> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            int gPoints = node.get("gPoints").asInt();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic11Data(coordinates, gPoints, expectedResult, errorMessage));
        }

        Lic11Data[] dataArray = new Lic11Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}