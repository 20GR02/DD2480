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

public class JudgeLic3Test {

    private final int LIC_INDEX = 3;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    @Test
    public void testJudgeLic3Negative() {
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic3Data[] dataArray = getLic3Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic3Data data : dataArray) {

            boolean result = licJudge.judgeLic3(data.coordinates, data.area1);

            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic3Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic3Data[] dataArray = getLic3Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic3Data data : dataArray) {
            boolean result = licJudge.judgeLic3(data.coordinates, data.area1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic3Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic3Data[] dataArray = getLic3Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic3Data data : dataArray) {
            boolean result = licJudge.judgeLic3(data.coordinates, data.area1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private record Lic3Data(Coordinate[] coordinates, double area1,
            boolean expectedResult, String errorMessage) {
    }

    private static Lic3Data[] getLic3Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic3Data> dataList = new ArrayList<>();

        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            double area1 = node.get("area1").asDouble();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic3Data(coordinates, area1, expectedResult, errorMessage));
        }

        Lic3Data[] dataArray = new Lic3Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}