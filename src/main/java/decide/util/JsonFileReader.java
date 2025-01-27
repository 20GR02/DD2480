package decide.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class JsonFileReader {
    /**
     * Returns the path to the JSON file containing negative test cases for a given LIC.
     *
     * @param licIndex the index of the LIC, starting from 0 to 14.
     * @return the path to the JSON file containing negative test cases for the given LIC.
     */
    public static String getJsonNegativePath(int licIndex) {
        return "decide/lic_judge/judge_lic" + licIndex + "/judge-lic" + licIndex + "-negative.json";
    }

    /**
     * Returns the path to the JSON file containing positive test cases for a given LIC.
     *
     * @param licIndex the index of the LIC, starting from 0 to 14.
     * @return the path to the JSON file containing positive test cases for the given LIC.
     */
    public static String getJsonPositivePath(int licIndex) {
        return "decide/lic_judge/judge_lic" + licIndex + "/judge-lic" + licIndex + "-positive.json";
    }

    /**
     * Returns the path to the JSON file containing valid test cases for a given LIC.
     *
     * @param licIndex the index of the LIC, starting from 0 to 14.
     * @return the path to the JSON file containing valid test cases for the given LIC.
     */
    public static String getJsonValidPath(int licIndex) {
        return "decide/lic_judge/judge_lic" + licIndex + "/judge-lic" + licIndex + "-valid.json";
    }

    /**
     * Loads a JSON file from the resources directory and parses it into a {@link JsonNode}.
     *
     * @param filePath the relative path to the JSON file within the resources directory.
     *                 For example, "decide/lic-judge/judge-lic0-case1.json".
     * @return a {@link JsonNode} representing the root of the JSON data structure.
     * @throws IllegalArgumentException if the file is not found at the specified path.
     * @throws RuntimeException         if there is an error while reading or parsing the JSON file.
     */
    public static JsonNode loadJsonAsNode(String filePath) {
        try {
            InputStream inputStream = JsonFileReader.class.getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + filePath);
            }
            return OBJECT_MAPPER.readTree(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load or parse JSON file: " + filePath, e);
        }
    }

    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.getObjectMapper();
}
