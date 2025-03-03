package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class Parser {
    public static Map<String, Object> parse(String data, String dataType) throws JsonProcessingException {
        ObjectMapper mapper = null;
        switch (dataType) {
            case "json" -> {
                mapper = new ObjectMapper();
            }
            case "yaml", "yml" -> {
                mapper = new YAMLMapper();
            }
            default -> throw new RuntimeException("Unknown data type" + dataType);
        }
        return mapper.readValue(data, Map.class);
    }
}
