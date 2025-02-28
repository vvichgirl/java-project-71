package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.List;
import java.util.Map;

public class Formatter {
    public static String getFormattedOutput(List<Map<String, Object>> diffDesc, String format) {
        return switch (format) {
            case "stylish" -> Stylish.getOutput(diffDesc);
            case "plain" -> Plain.getOutput(diffDesc);
            default -> throw new RuntimeException("Unknown format" + format);
        };
    }
}
