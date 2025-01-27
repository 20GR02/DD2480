package decide.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import decide.core.Decider;
import decide.model.Coordinate;
import decide.model.Parameters;
import decide.model.enums.ConnectorEnum;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JsonFileGenerator {


    /**
     * Generates a JSON file containing randomized input data for the {@link Decider#decide} method.
     * If the specified directory does not exist, it is created. The file is named in the format
     * "input[k].json", where `k` is the next available index in the directory.
     * <p>
     * The generated JSON file contains the following data structure:
     * <ul>
     *     <li>{@code numPoints} - the number of planar data points, randomly chosen between 2 and 100.</li>
     *     <li>{@code coordinates} - a list of {@link Coordinate} objects representing the data points,
     *     with x and y coordinates randomly chosen within a specified range.</li>
     *     <li>{@code parameters} - a {@link Parameters} object with randomly generated values for LIC parameters.</li>
     *     <li>{@code lcm} - a 15x15 matrix of {@link ConnectorEnum} values, randomly selected.</li>
     *     <li>{@code puv} - a boolean array of size 15, randomly initialized.</li>
     * </ul>
     *
     * @param path the directory in which the JSON file will be created
     * @return the name of the generated JSON file
     * @throws IOException if an error occurs while creating the directory or writing the file
     */
    public String generateDeciderInput(String path) throws IOException {
        // ensure directory exists
        Path directory = Paths.get(path);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        // determine the next available file name
        int k = 0;
        while (Files.exists(directory.resolve("input" + k + ".json"))) {
            k++;
        }
        String fileName = "input" + k + ".json";

        int minBound = -100;
        int maxBound = 100;

        int numPoints = randomInt(2, 100);

        List<Coordinate> coordinateList = new ArrayList<>();
        for (int i = 0; i < numPoints; i++) {
            int x = randomInt(minBound, maxBound);
            int y = randomInt(minBound, maxBound);
            coordinateList.add(new Coordinate(x, y));
        }
        Coordinate[] coordinatesArray = new Coordinate[coordinateList.size()];
        coordinateList.toArray(coordinatesArray);

        int length1 = randomInt(0, maxBound);
        int radius1 = randomInt(0, maxBound);
        double epsilon = randomDouble(0, Math.PI);
        int area1 = randomInt(0, maxBound);
        int qPoints = randomInt(2, numPoints);
        int quads = randomInt(1, 3);

        int nPoints = -1;
        int dist = -1;
        if (numPoints >= 3) {
            nPoints = randomInt(3, numPoints);
            dist = randomInt(0, maxBound);
        }

        int kPoints = -1;
        if (numPoints >= 3) {
            kPoints = randomInt(1, numPoints - 2);
        }

        int aPoints = -1;
        int bPoints = -1;
        if (numPoints >= 5) {
            int abPoints = randomInt(2, numPoints - 2);
            aPoints = randomInt(1, abPoints - 1);
            bPoints = abPoints - aPoints;
        }

        int cPoints = -1;
        int dPoints = -1;
        if (numPoints >= 5) {
            int cdPoints = randomInt(2, numPoints - 3);
            cPoints = randomInt(1, cdPoints - 1);
            dPoints = cdPoints - cPoints;
        }

        int ePoints = -1;
        int fPoints = -1;
        if (numPoints >= 5) {
            int efPoints = randomInt(2, numPoints - 3);
            ePoints = randomInt(1, efPoints - 1);
            fPoints = efPoints - ePoints;
        }

        int gPoints = -1;
        if (numPoints >= 3) {
            gPoints = randomInt(1, numPoints - 2);
        }

        int length2 = -1;
        if (numPoints < 3) {
            length2 = randomInt(0, maxBound);
        }

        int radius2 = -1;
        if (numPoints < 5) {
            radius2 = randomInt(0, maxBound);
        }

        int area2 = -1;
        if (numPoints < 5) {
            area2 = randomInt(0, maxBound);
        }

        Parameters parameters = new Parameters(
                length1, radius1, epsilon, area1, qPoints, quads, nPoints, dist, kPoints, aPoints, bPoints, cPoints,
                dPoints, ePoints, fPoints, gPoints, length2, radius2, area2);

        ConnectorEnum[][] lcm = new ConnectorEnum[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                lcm[i][j] = ConnectorEnum.random();
            }
        }

        // Generate PUV
        boolean[] puv = new boolean[15];
        for (int i = 0; i < 15; i++) {
            puv[i] = RANDOM.nextBoolean();
        }

        // Create the JSON object
        Decider.DeciderData mainData = new Decider.DeciderData(numPoints, coordinatesArray, parameters, lcm, puv);

        // Write JSON to file and return the file name
        OBJECT_MAPPER.writeValue(new File(directory.resolve(fileName).toString()), mainData);
        return fileName;
    }

    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();
    private static final Random RANDOM = new Random();

    private static int randomInt(int min, int max) {
        if (min > max) {
            return -1;
        }
        return RANDOM.nextInt(max - min + 1) + min;
    }

    private static double randomDouble(double min, double max) {
        if (min > max) {
            return -1;
        }
        return RANDOM.nextDouble(max - min) + min;
    }
}
