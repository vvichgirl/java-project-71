package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class Parser {
    public static Map<String, String> parse(String filePath, String file, String ext) {
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
        try {
            return mapper.readValue(file, Map.class);
        } catch (JsonProcessingException e) {
            System.out.println(ext + " format error in the file '" + filePath + "'");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
