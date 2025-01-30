package decide.decider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import decide.core.Decider;
import decide.model.Coordinate;
import decide.model.Parameters;
import decide.model.enums.ConnectorEnum;
import decide.util.JsonFileReader;
import decide.util.JsonUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeciderTest {
    @Test
    public void testDeciderNegative() throws IOException {
        String path = "decide/decider/decide/decide-negative.json";
        DecideData[] dataArray = getMakeLaunchDecisionData(path);

        Decider decider = new Decider();
        for (DecideData data : dataArray) {
            boolean result = decider.decide(data.numPoints, data.coordinates, data.parameters, data.lcm, data.puv);
            assertEquals(data.expectedResults, result, data.errorMessage);
        }
    }

    @Test
    public void testDeciderPositive() throws IOException {
        String path = "decide/decider/decide/decide-positive.json";
        DecideData[] dataArray = getMakeLaunchDecisionData(path);

        Decider decider = new Decider();
        for (DecideData data : dataArray) {
            boolean result = decider.decide(data.numPoints, data.coordinates, data.parameters, data.lcm, data.puv);
            assertEquals(data.expectedResults, result, data.errorMessage);
        }
    }

    @Test
    public void testDeciderValid() throws IOException {
        String path = "decide/decider/decide/decide-valid.json";
        DecideData[] dataArray = getMakeLaunchDecisionData(path);

        Decider decider = new Decider();
        for (DecideData data : dataArray) {
            boolean result = decider.decide(data.numPoints, data.coordinates, data.parameters, data.lcm, data.puv);
            assertEquals(data.expectedResults, result, data.errorMessage);
        }
    }

    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    private record DecideData(int numPoints, Coordinate[] coordinates,
                              Parameters parameters, ConnectorEnum[][] lcm, boolean[] puv,
                              boolean expectedResults, String errorMessage) {
    }

    private static DecideData[] getMakeLaunchDecisionData(String path) throws JsonProcessingException {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<DecideData> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            int numPoints = node.get("numPoints").asInt();
            Coordinate[] coordinates = OBJECT_MAPPER.convertValue(node.get("coordinates"), Coordinate[].class);
            Parameters parameters = OBJECT_MAPPER.treeToValue(node.get("parameters"), Parameters.class);
            ConnectorEnum[][] lcm = OBJECT_MAPPER.convertValue(node.get("lcm"), ConnectorEnum[][].class);
            boolean[] puv = OBJECT_MAPPER.convertValue(node.get("puv"), boolean[].class);

            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new DecideData(numPoints, coordinates, parameters, lcm, puv, expectedResult, errorMessage));
        }

        DecideData[] dataArray = new DecideData[dataList.size()];
        return dataList.toArray(dataArray);
    }
}
