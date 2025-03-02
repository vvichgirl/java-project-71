package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.List;
import java.util.Map;

public class Formatter {
    public static String getFormattedOutput(
            List<Map<String, Object>> diffDesc, String format
    ) throws JsonProcessingException {
        return switch (format) {
            case "stylish" -> Stylish.getOutput(diffDesc);
            case "plain" -> Plain.getOutput(diffDesc);
            case "json" -> Json.getOutput(diffDesc);
            default -> throw new RuntimeException("Unknown format" + format);
        };
    }
}
