package decide.decider;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import decide.core.Decider;
import decide.model.enums.ConnectorEnum;
import decide.util.JsonFileReader;
import decide.util.JsonUtil;

public class GeneratePumTest {

    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    private record PumData(boolean[] cmv, ConnectorEnum[][] lcm, boolean[][] expectedResults, String errorMessage) {
    }

    public static boolean areEqual(boolean[][] matrix1, boolean[][] matrix2) {
        if (matrix1 == matrix2)
            return true; // Same reference or both null
        if (matrix1 == null || matrix2 == null)
            return false; // One is null and the other is not
        if (matrix1.length != matrix2.length)
            return false; // Different number of rows

        // Check each row length and content
        for (int i = 0; i < matrix1.length; i++) {
            if (matrix1[i].length != matrix2[i].length)
                return false; // Different number of columns
            for (int j = 0; j < matrix1[i].length; j++) {
                if (matrix1[i][j] != matrix2[i][j])
                    return false; // Values are not equal
            }
        }

        return true; // All checks passed
    }

    @Test
    public void testPumPositive() {
        String path = "decide/decider/generate_pum/generate-pum-positive.json";
        PumData[] dataArray = getPumData(path);

        for (PumData data : dataArray) {
            boolean[][] result = Decider.generatePum(data.cmv, data.lcm);
            assertEquals(true, areEqual(data.expectedResults, result));
        }
    }

    @Test
    public void testPumNegative() {
        String path = "decide/decider/generate_pum/generate-pum-negative.json";
        PumData[] dataArray = getPumData(path);

        for (PumData data : dataArray) {
            boolean[][] result = Decider.generatePum(data.cmv, data.lcm);
            assertEquals(false, areEqual(data.expectedResults, result));
        }
    }

    @Test
    public void testPumValid() {
        String path = "decide/decider/generate_pum/generate-pum-valid.json";
        PumData[] dataArray = getPumData(path);

        for (PumData data : dataArray) {
            assertThrows(IllegalArgumentException.class, () -> {
                Decider.generatePum(data.cmv, data.lcm);
            }, "Expected IllegalArgumentException to be thrown when the input is invalid");
        }
    }

    private static PumData[] getPumData(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<PumData> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            boolean[] cmv = OBJECT_MAPPER.convertValue(node.get("cmv"), boolean[].class);
            ConnectorEnum[][] lcm = OBJECT_MAPPER.convertValue(node.get("lcm"), ConnectorEnum[][].class);

            boolean[][] expectedResult = OBJECT_MAPPER.convertValue(node.get("expectedResult"), boolean[][].class);

            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new PumData(cmv, lcm, expectedResult, errorMessage));
        }

        PumData[] dataArray = new PumData[dataList.size()];
        return dataList.toArray(dataArray);
    }

}