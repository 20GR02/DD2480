package decide.lic_judge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Loads a JSON file from the resources directory and parses it into a {@link com.fasterxml.jackson.databind.JsonNode}.
     *
     * @param filePath the relative path to the JSON file within the resources directory.
     *                 For example, "decide/lic-judge/judge-lic0-case1.json".
     * @return a {@link com.fasterxml.jackson.databind.JsonNode} representing the root of the JSON data structure.
     * @throws IllegalArgumentException if the file is not found at the specified path.
     * @throws RuntimeException         if there is an error while reading or parsing the JSON file.
     */
    public static JsonNode loadJsonAsNode(String filePath) {
        try {
            InputStream inputStream = JsonUtil.class.getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + filePath);
            }
            return objectMapper.readTree(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load or parse JSON file: " + filePath, e);
        }
    }
}
