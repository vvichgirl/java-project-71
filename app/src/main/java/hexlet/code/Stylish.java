package hexlet.code;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Stylish {
    private static final Map<String, String> STATUSES = Map.of("added", "+", "deleted", "-", "unchanged", "");

    public static String getOutput(List<Map<String, String>> diffDesc) {
        var space = " ";
        var diffResult = diffDesc.stream()
                .map(diff -> {
                    var indent = diff.get("status").equals("unchanged") ? space.repeat(3) : space.repeat(2);
                    return indent
                            + STATUSES.get(diff.get("status"))
                            + space
                            + diff.get("key")
                            + ": "
                            + diff.get("value");
                })
                .collect(Collectors.joining("\n"));
        return "{\n" + diffResult + "\n}";
    }
}
