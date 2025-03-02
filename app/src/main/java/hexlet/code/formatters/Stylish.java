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
        var diffResult = diffDesc.stream()
                .map(diff -> {
                    var status = diff.get("status");
                    var indent = status.equals("unchanged") ? "   " : "  ";
                    var key = diff.get("key");
                    var value = status.equals("updated") ? diff.get("valueOld") : diff.get("value");
                    value =  String.valueOf(value);
                    var valueNew = String.valueOf(diff.get("valueNew"));
                    if (diff.get("status").equals("updated")) {
                        return String.format(TEMPLATE, indent, STATUSES.get("removed"), key, value)
                                + String.format(TEMPLATE, indent, STATUSES.get("added"), key, valueNew);
                    } else {
                        return String.format(TEMPLATE, indent, STATUSES.get(diff.get("status")), key, value);
                    }
                })
                .collect(Collectors.joining(""));
        return "{\n" + diffResult + "}";
    }
}
