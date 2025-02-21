package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;

public class Differ {
    private static Path getPath(String fileName) {
        Path path = null;
        try {
            path = Paths.get("src", "main", "resources", fileName)
                    .toAbsolutePath().normalize();
        } catch (InvalidPathException e) {
            System.out.println("Path Error " + e);
        }
        return path;
    }

    private static String readFile(String fileName) throws IOException {
        Path path = getPath(fileName);
        return new String(Files.readString(path).trim());
    }

    public static String generate(String filePath1, String filePath2) {
        String file1;
        String file2;
        ObjectMapper mapper1 = new ObjectMapper();
        ObjectMapper mapper2 = new ObjectMapper();
        Map<String, String> mapFile1;
        Map<String, String> mapFile2;

        try {
            file1 = readFile(filePath1);
        } catch (IOException e) {
            return "Check the file '" + filePath1 + "' exist and the access.";
        }
        try {
            file2 = readFile(filePath2);
        } catch (IOException e) {
            return "Check the file '" + filePath2 + "' exist and the access.";
        }

        try {
            mapFile1 = mapper1.readValue(file1, Map.class);
        } catch (JsonProcessingException e) {
            return "Json format error in the file '" + filePath1 + "'";
        }
        try {
            mapFile2 = mapper2.readValue(file2, Map.class);
        } catch (JsonProcessingException e) {
            return "Json format error in the file '" + filePath2 + "'";
        }

        Set<String> keys = new TreeSet<>(mapFile1.keySet());
        keys.addAll(mapFile2.keySet());

        var result = keys.stream()
                .reduce("{\n", (diff, key) -> diff + getDiff(key, mapFile1, mapFile2));

        result += "}";
        return result;
    }

    public static String getDiff(String key, Map<String, String> map1, Map<String, String> map2) {
        var value1 = String.valueOf(map1.get(key));
        var value2 = String.valueOf(map2.get(key));
        var indent = "  ";
        var diffResult = "";
        if (!map1.containsKey(key)) {
            diffResult = indent + "+ " + key + ": " + value2 + "\n";
        }
        if (!map2.containsKey(key)) {
            diffResult = indent + "- " + key + ": " + value1 + "\n";
        }
        if (map1.containsKey(key) && map2.containsKey(key)) {
            if (value1.equals(value2)) {
                diffResult = indent + "  " + key + ": " + value1 + "\n";
            } else {
                diffResult = indent + "- " + key + ": " + value1 + "\n";
                diffResult += indent + "+ " + key + ": " + value2 + "\n";
            }
        }
        return diffResult;
    }
}
