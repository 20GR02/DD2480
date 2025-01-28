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

public class JudgeLic6Test {
    @Test
    public void testJudgeLic6Negative() {
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic6Data[] dataArray = getLic6Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic6Data data : dataArray) {
            boolean result = licJudge.judgeLic6(data.coordinates, data.nPoints, data.dist);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic6Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic6Data[] dataArray = getLic6Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic6Data data : dataArray) {
            boolean result = licJudge.judgeLic6(data.coordinates, data.nPoints, data.dist);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic6Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic6Data[] dataArray = getLic6Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic6Data data : dataArray) {
            boolean result = licJudge.judgeLic6(data.coordinates, data.nPoints, data.dist);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private final int LIC_INDEX = 6;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    private record Lic6Data(Coordinate[] coordinates, int nPoints, double dist
            , boolean expectedResult, String errorMessage) {
    }

    private static Lic6Data[] getLic6Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic6Data> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            int nPoints = node.get("nPoints").asInt();        
            double dist = node.get("dist").asDouble();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic6Data(coordinates, nPoints, dist, expectedResult, errorMessage));
        }

        Lic6Data[] dataArray = new Lic6Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}

