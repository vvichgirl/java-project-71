package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Comparator {
    public static List<Map<String, String>> getDiff(
            Set<String> keys, Map<String, String> map1, Map<String, String> map2
    ) {
        List<Map<String, String>> result = new ArrayList<>();
        keys.forEach(key -> {
            var value1 = String.valueOf(map1.get(key));
            var value2 = String.valueOf(map2.get(key));
            if (!map1.containsKey(key)) {
                result.add(Map.of("status", "added", "key", key, "value", value2));
            }
            if (!map2.containsKey(key)) {
                result.add(Map.of("status", "deleted", "key", key, "value", value1));
            }
            if (map1.containsKey(key) && map2.containsKey(key)) {
                if (value1.equals(value2)) {
                    result.add(Map.of("status", "unchanged", "key", key, "value", value1));
                } else {
                    result.add(Map.of("status", "deleted", "key", key, "value", value1));
                    result.add(Map.of("status", "added", "key", key, "value", value2));
                }
            }
        });
        return result;
    }
}
