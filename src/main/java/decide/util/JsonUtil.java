package decide.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private JsonUtil() {
    }

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
