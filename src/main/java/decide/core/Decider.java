package decide.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import decide.model.Coordinate;
import decide.model.Parameters;
import decide.model.enums.ConnectorEnum;
import decide.util.JsonFileGenerator;
import decide.util.JsonUtil;

public class Decider {
    /**
     * Represents the input data structure for {@link Decider#decide} method.
     *
     * @param numPoints   the number of planar data points
     * @param coordinates array containing the coordinates of the points
     * @param parameters  parameters for LICs
     * @param lcm         logical connector matrix
     * @param puv         preliminary unlocking vector
     */
    public record DeciderData(int numPoints, Coordinate[] coordinates,
            Parameters parameters, ConnectorEnum[][] lcm, boolean[] puv) {
    }

    /**
     * Decide whether to launch based on the input parameters
     *
     * @param numPoints   the number of planar data points
     * @param coordinates array containing the coordinates of the points
     * @param parameters  parameters for LICs
     * @param lcm         logical connector matrix
     * @param puv         preliminary unlocking vector
     * @return the launch decision
     */
    public boolean decide(int numPoints,
            Coordinate[] coordinates,
            Parameters parameters,
            ConnectorEnum[][] lcm,
            boolean[] puv) {
        if (coordinates.length != numPoints) {
            return false;
        }

        this.coordinates = coordinates;
        this.numPoints = numPoints;
        this.lcm = lcm;
        this.puv = puv;

        LicJudge licJudge = new LicJudge();
        cmv = licJudge.judge(coordinates, parameters);

        pum = generatePum(cmv, this.lcm);

        fuv = generateFuv(pum, this.puv);

        launch = makeLaunchDecision(fuv);

        return launch;
    }

    public void run() throws IOException {
        String path = "src/main/resources/decide";
        Path directory = Path.of(path);

        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        String fileName;
        try (Stream<Path> filesStream = Files.list(directory)) {
            fileName = filesStream
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(fileNameCandidate -> Pattern.matches("input\\d+\\.json", fileNameCandidate))
                    .findFirst()
                    .orElseGet(() -> {
                        try {
                            JsonFileGenerator generator = new JsonFileGenerator();
                            return generator.generateDeciderInput(path);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to generate decide input file", e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Failed to list files in directory", e);
        }

        JsonNode jsonNode = OBJECT_MAPPER.readTree(Path.of(path, fileName).toFile());

        int numPoints = jsonNode.get("numPoints").asInt();

        Coordinate[] coordinates = OBJECT_MAPPER.convertValue(
                jsonNode.get("coordinates"), Coordinate[].class);
        Parameters parameters = OBJECT_MAPPER.treeToValue(jsonNode.get("parameters"), Parameters.class);
        ConnectorEnum[][] lcm = OBJECT_MAPPER.convertValue(
                jsonNode.get("lcm"), ConnectorEnum[][].class);
        boolean[] puv = OBJECT_MAPPER.convertValue(jsonNode.get("puv"), boolean[].class);

        DeciderData data = new DeciderData(numPoints, coordinates, parameters, lcm, puv);

        boolean launch = decide(data.numPoints, data.coordinates, data.parameters, data.lcm, data.puv);

        System.out.printf("file: %s, launch: %b%n", fileName, launch);
    }

    private Coordinate[] coordinates;
    private int numPoints;
    private boolean[] cmv;
    private ConnectorEnum[][] lcm;
    private boolean[][] pum;
    private boolean[] puv;
    private boolean[] fuv;
    private boolean launch;

    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();

    /**
     * Generate PUM (Preliminary Unlocking Matrix) based on CMV (Conditions Met
     * Vector) and LCM (Logical Connector Matrix)
     *
     * @param cmv Conditions Met Vector
     * @param lcm Logical Connector Matrix
     * @return generated PUM
     */
    public static boolean[][] generatePum(boolean[] cmv, ConnectorEnum[][] lcm) {

        if (cmv.length < 1 || lcm[0].length < 1) {
            throw new IllegalArgumentException("LCM and CMV is empty");
        }

        if (cmv.length != lcm[0].length) {
            throw new IllegalArgumentException("LCM and CMV is not the same sizes");
        }

        int length = cmv.length;
        boolean[][] pum = new boolean[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                switch (lcm[i][j]) {
                    case ORR:
                        pum[i][j] = cmv[i] || cmv[j];
                        break;
                    case ANDD:
                        pum[i][j] = cmv[i] && cmv[j];
                        break;
                    case NOTUSED:
                        pum[i][j] = true;
                        break;
                }
            }
        }

        return pum;
    }

    /**
     * Generate FUV (Final Unlocking Vector) based on PUM (Preliminary Unlocking
     * Matrix) and PUV (Preliminary Unlocking Vector)
     *
     * @param pum Preliminary Unlocking Matrix
     * @param puv Preliminary Unlocking Vector
     * @return generated FUV
     */
    public static boolean[] generateFuv(boolean[][] pum, boolean[] puv) {

        if (pum[0].length == 0 || puv.length == 0) {
            throw new IllegalArgumentException("PUM or PUV are empty");
        }

        if (pum[0].length != puv.length) {
            throw new IllegalArgumentException("Mismatch between PUM and PUV dimensions");
        }

        int length = puv.length;
        boolean[] fuv = new boolean[length];

        for (int i = 0; i < length; i++) {
            if (!puv[i]) {
                fuv[i] = true;
            } else {
                boolean allTrue = true;
                for (int j = 0; j < length; j++) {
                    if (!pum[i][j]) {
                        allTrue = false;
                        break;
                    }
                }
                fuv[i] = allTrue;
            }
        }

        return fuv;
    }

    /**
     * Whether to generate launch-unlock signal
     *
     * @param fuv Final Unlocking Vector
     * @return final launch / no launch decision
     */
    public static boolean makeLaunchDecision(boolean[] fuv) {
        boolean launchDecision = true;

        for (int i = 0; i < fuv.length; i++) {
            if (!fuv[i]) {
                launchDecision = false;
                break;
            }
        }

        return launchDecision;
    }
}
