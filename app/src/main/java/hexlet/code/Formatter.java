package hexlet.code;

import java.util.List;
import java.util.Map;

public class Formatter {
    public static String getFormattedOutput(List<Map<String, String>> diffDesc, String format) {
        if (format.equals("stylish")) {
            return Stylish.getOutput(diffDesc);
        } else {
            throw new RuntimeException("Unknown format " + format);
        }
    }
}
