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

public class JudgeLic0Test {
    @Test
    public void testJudgeLic0Negative() {
        // load JSON file as JsonNode object
        String path = JsonFileReader.getJsonNegativePath(LIC_INDEX);
        Lic0Data[] dataArray = getLic0Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic0Data data : dataArray) {
            // get return value of tested method
            boolean result = licJudge.judgeLic0(data.coordinates, data.length1);

            // verify the return value
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic0Positive() {
        String path = JsonFileReader.getJsonPositivePath(LIC_INDEX);
        Lic0Data[] dataArray = getLic0Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic0Data data : dataArray) {
            boolean result = licJudge.judgeLic0(data.coordinates, data.length1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    @Test
    public void testJudgeLic0Valid() {
        String path = JsonFileReader.getJsonValidPath(LIC_INDEX);
        Lic0Data[] dataArray = getLic0Data(path);

        LicJudge licJudge = new LicJudge();
        for (Lic0Data data : dataArray) {
            boolean result = licJudge.judgeLic0(data.coordinates, data.length1);
            assertEquals(data.expectedResult, result, data.errorMessage);
        }
    }

    private final int LIC_INDEX = 0;
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    /**
     * Represents the input data structure for LIC0.
     *
     * @param coordinates    An array of {@link Coordinate} objects representing the points used in the test case.
     * @param length1        A double value representing the length parameter used for LIC0 evaluation.
     * @param expectedResult A boolean indicating the expected result of the test case.
     * @param errorMessage   A string message to describe the error if the test fails.
     */
    private record Lic0Data(Coordinate[] coordinates, double length1,
                            boolean expectedResult, String errorMessage) {
    }

    /**
     * Parses a JSON file to extract LIC0 test data.
     * <p>
     * The JSON file should contain an array of test cases, where each test case includes:
     * <ul>
     * <li> An array of coordinates representing 2D points. </li>
     * <li> A length parameter used for LIC0 evaluation. </li>
     * <li> An expected result indicating whether the LIC0 condition should be satisfied. </li>
     * <li> An error message describing the case in case of a failure. </li>
     * </ul>
     *
     * @param path The file path to the JSON file containing the LIC0 test data.
     * @return An array of {@link Lic0Data} objects representing the parsed test cases.
     * @throws IllegalArgumentException if the JSON file structure is invalid or fields are missing.
     */
    private static Lic0Data[] getLic0Data(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<Lic0Data> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                    node.get("coordinates"), Coordinate[].class);
            double length1 = node.get("length1").asDouble();
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new Lic0Data(coordinates, length1, expectedResult, errorMessage));
        }

        Lic0Data[] dataArray = new Lic0Data[dataList.size()];
        return dataList.toArray(dataArray);
    }
}