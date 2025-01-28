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

public class JudgeLic10Test {

    private final int LIC_INDEX = 10;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    @Test
    public void testJudgeLic10Negative() {
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic10Data[] dataArray = getLic10Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic10Data data : dataArray) {

            boolean result = licJudge.judgeLic10(data.coordinates, data.ePoints, data.fPoints, data.area1);

            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic10Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic10Data[] dataArray = getLic10Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic10Data data : dataArray) {
            boolean result = licJudge.judgeLic10(data.coordinates, data.ePoints, data.fPoints, data.area1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic10Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic10Data[] dataArray = getLic10Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic10Data data : dataArray) {
            boolean result = licJudge.judgeLic10(data.coordinates, data.ePoints, data.fPoints, data.area1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private record Lic10Data(Coordinate[] coordinates, int ePoints, int fPoints, double area1,
            boolean expectedResult, String errorMessage) {
    }

    private static Lic10Data[] getLic10Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic10Data> dataList = new ArrayList<>();

        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            int ePoints = node.get("ePoints").asInt();
            int fPoints = node.get("fPoints").asInt();
            double area1 = node.get("area1").asDouble();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic10Data(coordinates, ePoints, fPoints, area1, expectedResult, errorMessage));
        }

        Lic10Data[] dataArray = new Lic10Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}