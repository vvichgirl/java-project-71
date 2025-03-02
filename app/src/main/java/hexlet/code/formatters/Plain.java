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
                    var status = diff.get("status");
                    var template = TEMPLATE.get(status);
                    var value = status.equals("updated") ? diff.get("valueOld") : diff.get("value");
                    value =  getValueFormatted(value);
                    var valueNew = getValueFormatted(diff.get("valueNew"));
                    return String.format(template, diff.get("key"), value, valueNew);
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
