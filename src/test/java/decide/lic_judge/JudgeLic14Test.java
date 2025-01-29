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

public class JudgeLic14Test {

    private final int LIC_INDEX = 14;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    @Test
    public void testJudgeLic14Negative() {
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic14Data[] dataArray = getLic14Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic14Data data : dataArray) {

            boolean result = licJudge.judgeLic14(data.coordinates, data.ePoints, data.fPoints, data.area1, data.area2);

            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic14Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic14Data[] dataArray = getLic14Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic14Data data : dataArray) {
            boolean result = licJudge.judgeLic14(data.coordinates, data.ePoints, data.fPoints, data.area1, data.area2);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic14Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic14Data[] dataArray = getLic14Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic14Data data : dataArray) {
            boolean result = licJudge.judgeLic14(data.coordinates, data.ePoints, data.fPoints, data.area1, data.area2);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private record Lic14Data(Coordinate[] coordinates, int ePoints, int fPoints, double area1, double area2,
            boolean expectedResult, String errorMessage) {
    }

    private static Lic14Data[] getLic14Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic14Data> dataList = new ArrayList<>();

        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            int ePoints = node.get("ePoints").asInt();
            int fPoints = node.get("fPoints").asInt();
            double area1 = node.get("area1").asDouble();
            double area2 = node.get("area2").asDouble();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic14Data(coordinates, ePoints, fPoints, area1, area2, expectedResult, errorMessage));
        }

        Lic14Data[] dataArray = new Lic14Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}