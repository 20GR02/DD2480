package decide;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LicJudgeTest {
    @Test
    public void testJudgeLic0() {
        // load JSON file as JsonNode object
        String judgeLic1Case1Path = "decide/lic-judge/judge-lic0-case1.json";
        JsonNode jsonNode = JsonUtil.loadJsonAsNode(judgeLic1Case1Path);

        // get input from JsonNode object
        double length1 = jsonNode.get("length1").asDouble();
        boolean expectedResult = jsonNode.get("expectedResult").asBoolean();
        JsonNode coordinatesNode = jsonNode.get("coordinates");
        Coordinate[] coordinates = new Coordinate[coordinatesNode.size()];
        for (int i = 0; i < coordinatesNode.size(); i++) {
            JsonNode point = coordinatesNode.get(i);
            double pointX = point.get("x").asDouble();
            double pointY = point.get("y").asDouble();
            coordinates[i] = new Coordinate(pointX, pointY);
        }

        // get return value of tested method
        LicJudge licJudge = new LicJudge();
        boolean result = licJudge.judgeLic0(coordinates, length1);

        // verify return value
        assertEquals(expectedResult, result);
    }
}