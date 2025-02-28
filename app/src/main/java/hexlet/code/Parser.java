package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class Parser {
    public static Map<String, Object> parse(String filePath, String file, String ext) throws JsonProcessingException {
        ObjectMapper mapper = null;
        switch (ext) {
            case "json" -> {
                mapper = new ObjectMapper();
            }
            case "yaml", "yml" -> {
                mapper = new YAMLMapper();
            }
            default -> throw new RuntimeException("Unknown file extension" + ext);
        }
        return mapper.readValue(file, Map.class);
    }
}
