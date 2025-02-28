package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Plain {
    private static final Map<String, String> TEMPLATE = Map.of(
            "added", "Property '%s' was added with value: %s",
            "removed", "Property '%s' was removed",
            "updated", "Property '%s' was updated. From %s to %s"
    );

    public static String getOutput(List<Map<String, Object>> diffDesc) {
        var diffResult = diffDesc.stream()
                .filter(diff -> !diff.get("status").equals("unchanged"))
                .map(diff -> {
                    var template = TEMPLATE.get(diff.get("status"));
                    var value = getValueFormatted(diff.get("value"));
                    var value2 = getValueFormatted(diff.get("value2"));
                    return String.format(template, diff.get("key"), value, value2);
                })
                .collect(Collectors.joining("\n"));
        return diffResult;
    }

    public static String getValueFormatted(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }
        if (value instanceof List || value instanceof Map) {
            return "[complex value]";
        }
        return String.valueOf(value);
    }
}
