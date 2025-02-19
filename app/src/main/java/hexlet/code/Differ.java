package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Differ {
    private static Path getPath(String fileName) {
        Path path = null;
        try {
            path = Paths.get("src", "main", "resources", fileName)
                    .toAbsolutePath().normalize();
        } catch(InvalidPathException e) {
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
        Map<String, String> parseFile1 = null;
        Map<String, String> parseFile2 = null;

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
            parseFile1 = mapper1.readValue(file1, Map.class);
        } catch (JsonProcessingException e) {
            return "Json format error in the file '" + filePath1 + "'";
        }
        try {
            parseFile2 = mapper2.readValue(file2, Map.class);
        } catch (JsonProcessingException e) {
            return "Json format error in the file '" + filePath2 + "'";
        }

        return parseFile1.get("proxy");
    }
}
