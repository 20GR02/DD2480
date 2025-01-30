package decide.decider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import decide.core.Decider;
import decide.util.JsonFileReader;
import decide.util.JsonUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MakeLaunchDecisionTest {
    @Test
    public void testMakeLaunchDecisionNegative() {
        String path = "decide/decider/make_launch_decision/make-launch-decision-negative.json";
        MakeLaunchDecisionData[] dataArray = getMakeLaunchDecisionData(path);

        Decider decider = new Decider();
        for (MakeLaunchDecisionData data : dataArray) {
            boolean result = Decider.makeLaunchDecision(data.fuv);
            assertEquals(data.expectedResults, result, data.errorMessage);
        }
    }

    @Test
    public void testMakeLaunchDecisionPositive() {
        String path = "decide/decider/make_launch_decision/make-launch-decision-positive.json";
        MakeLaunchDecisionData[] dataArray = getMakeLaunchDecisionData(path);

        Decider decider = new Decider();
        for (MakeLaunchDecisionData data : dataArray) {
            boolean result = Decider.makeLaunchDecision(data.fuv);
            assertEquals(data.expectedResults, result, data.errorMessage);
        }
    }

    @Test
    public void testMakeLaunchDecisionValid() {
        String path = "decide/decider/make_launch_decision/make-launch-decision-valid.json";
        MakeLaunchDecisionData[] dataArray = getMakeLaunchDecisionData(path);

        Decider decider = new Decider();
        for (MakeLaunchDecisionData data : dataArray) {
            boolean result = Decider.makeLaunchDecision(data.fuv);
            assertEquals(data.expectedResults, result, data.errorMessage);
        }
    }

    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    private record MakeLaunchDecisionData(boolean[] fuv,
                                          boolean expectedResults, String errorMessage) {
    }

    private static MakeLaunchDecisionData[] getMakeLaunchDecisionData(String path) {
        JsonNode jsonNode = JsonFileReader.loadJsonAsNode(path);

        List<MakeLaunchDecisionData> dataList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            boolean[] fuv = OBJECT_MAPPER.convertValue(node.get("fuv"), boolean[].class);
            boolean expectedResult = node.get("expectedResult").asBoolean();
            String errorMessage = node.get("errorMessage").asText();

            dataList.add(new MakeLaunchDecisionData(fuv, expectedResult, errorMessage));
        }

        MakeLaunchDecisionData[] dataArray = new MakeLaunchDecisionData[dataList.size()];
        return dataList.toArray(dataArray);
    }
}
