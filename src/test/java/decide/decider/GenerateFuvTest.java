package decide.decider;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import decide.core.Decider;
import decide.util.JsonFileReader;
import decide.util.JsonUtil;

public class GenerateFuvTest {

    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    private record FuvData(boolean[][] pum, boolean[] puv, boolean[] expectedResults, String errorMessage) {
    }

    public static boolean areEqual(boolean[] vector1, boolean[] vector2) {
        if (vector1 == vector2)
            return true;
        if (vector1 == null || vector2 == null)
            return false;
        if (vector1.length != vector2.length)
            return false;

        for (int i = 0; i < vector1.length; i++) {
            if (vector1[i] != vector2[i]) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void testFuvPositive() {
        String path = "decide/decider/generate_fuv/generate-fuv-positive.json";
        FuvData[] dataArray = getFuvData(path);

        for (FuvData data : dataArray) {
            boolean[] result = Decider.generateFuv(data.pum, data.puv);
            assertEquals(true, areEqual(data.expectedResults, result));
        }
    }

    @Test
    public void testFuvNegative() {
        String path = "decide/decider/generate_fuv/generate-fuv-negative.json";
        FuvData[] dataArray = getFuvData(path);

        for (FuvData data : dataArray) {
            boolean[] result = Decider.generateFuv(data.pum, data.puv);
            assertEquals(false, areEqual(data.expectedResults, result));
        }
    }

    @Test
    public void testFuvValid() {
        String path = "decide/decider/generate_fuv/generate-fuv-valid.json";
        FuvData[] dataArray = getFuvData(path);

        for (FuvData data : dataArray) {
            assertThrows(IllegalArgumentException.class, () -> {
                Decider.generateFuv(data.pum, data.puv);
            }, "Expected IllegalArgumentException to be thrown when the input is invalid");
        }
    }

    private static FuvData[] getFuvData(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<FuvData> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            boolean[][] pum = OBJECT_MAPPER.convertValue(node.get("pum"), boolean[][].class);
            boolean[] puv = OBJECT_MAPPER.convertValue(node.get("puv"), boolean[].class);

            boolean[] expectedResult = OBJECT_MAPPER.convertValue(node.get("expectedResult"), boolean[].class);

            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new FuvData(pum, puv, expectedResult, errorMessage));
        }

        FuvData[] dataArray = new FuvData[dataList.size()];
        return dataList.toArray(dataArray);
    }

}