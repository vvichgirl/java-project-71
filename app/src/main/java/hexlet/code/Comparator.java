package hexlet.code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Comparator {
    public static List<Map<String, Object>> getDiff(
            Set<String> keys, Map<String, Object> map1, Map<String, Object> map2
    ) {
        List<Map<String, Object>> result = new ArrayList<>();
        keys.forEach(key -> {
            Object value1 = map1.get(key);
            Object value2 = map2.get(key);
            Map<String, Object> mapComparison = new LinkedHashMap<String, Object>();
            mapComparison.put("key", key);
            if (!map1.containsKey(key)) {
                mapComparison.put("status", "added");
                mapComparison.put("value", value2);
            } else if (!map2.containsKey(key)) {
                mapComparison.put("status", "removed");
                mapComparison.put("value", value1);
            } else if (Objects.equals(value1, value2)) {
                mapComparison.put("status", "unchanged");
                mapComparison.put("value", value1);
            } else {
                mapComparison.put("status", "updated");
                mapComparison.put("valueOld", value1);
                mapComparison.put("valueNew", value2);
            }
            result.add(mapComparison);
        });
        return result;
    }
}
