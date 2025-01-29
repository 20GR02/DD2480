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

public class JudgeLic4Test {
    @Test
    public void testJudgeLic4Negative() {
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic4Data[] dataArray = getLic4Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic4Data data : dataArray) {
            boolean result = licJudge.judgeLic4(data.coordinates, data.kPoints, data.quads);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic4Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic4Data[] dataArray = getLic4Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic4Data data : dataArray) {
            boolean result = licJudge.judgeLic4(data.coordinates, data.kPoints, data.quads);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic4Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic4Data[] dataArray = getLic4Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic4Data data : dataArray) {
            boolean result = licJudge.judgeLic4(data.coordinates, data.kPoints, data.quads);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private final int LIC_INDEX = 4;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    private record Lic4Data(Coordinate[] coordinates, int kPoints, int quads, boolean expectedResult,
            String errorMessage) {
    }

    private static Lic4Data[] getLic4Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic4Data> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            int kPoints = node.get("kPoints").asInt();
            int quads = node.get("quads").asInt();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic4Data(coordinates, kPoints, quads, expectedResult, errorMessage));
        }

        Lic4Data[] dataArray = new Lic4Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}
