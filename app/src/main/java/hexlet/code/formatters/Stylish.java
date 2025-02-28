package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Stylish {
    private static final Map<String, String> STATUSES = Map.of(
            "added", "+",
            "removed", "-",
            "unchanged", ""
    );

    private static final String TEMPLATE = "%s%s %s: %s\n";

    public static String getOutput(List<Map<String, Object>> diffDesc) {
        var space = " ";
        var diffResult = diffDesc.stream()
                .map(diff -> {
                    var indent = diff.get("status").equals("unchanged") ? space.repeat(3) : space.repeat(2);
                    var key = diff.get("key");
                    var value = String.valueOf(diff.get("value"));
                    var value2 = String.valueOf(diff.get("value2"));
                    if (diff.get("status").equals("updated")) {
                        return String.format(TEMPLATE, indent, STATUSES.get("removed"), key, value)
                                + String.format(TEMPLATE, indent, STATUSES.get("added"), key, value2);
                    } else {
                        return String.format(TEMPLATE, indent, STATUSES.get(diff.get("status")), key, value);
                    }
                })
                .collect(Collectors.joining(""));
        return "{\n" + diffResult + "}";
    }
}
