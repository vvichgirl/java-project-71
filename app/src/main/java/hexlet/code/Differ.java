package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;

public class Differ {
    private static Path getPath(String filePath) {
        Path path = null;
        try {
            path = Paths.get(filePath).toAbsolutePath().normalize();
        } catch (InvalidPathException e) {
            System.out.println("Path Error " + e);
        }
        return path;
    }

    private static String readFile(String filePath) throws IOException {
        Path path = getPath(filePath);
        return new String(Files.readString(path).trim());
    }

    public static String getFileExtension(String filePath) {
        int index = filePath.lastIndexOf('.');
        return filePath.substring(index + 1);
    }

    public static String generate(String filePath1, String filePath2, String format) {
        String file1;
        String file2;

        try {
            file1 = readFile(filePath1);
        } catch (IOException e) {
            System.out.println("Check the file '" + filePath1 + "' exist and the access.");
            return "";
        }
        try {
            file2 = readFile(filePath2);
        } catch (IOException e) {
            System.out.println("Check the file '" + filePath2 + "' exist and the access.");
            return "";
        }

        var ext1 = getFileExtension(filePath1);
        var ext2 = getFileExtension(filePath2);

        Map<String, String> mapFile1 = null;
        Map<String, String> mapFile2 = null;
        try {
            mapFile1 = Parser.parse(filePath1, file1, ext1);
        } catch (JsonProcessingException e) {
            System.out.println(ext1 + " format error in the file '" + filePath1 + "'");
            System.out.println(e.getMessage());
            return "";
        }
        try {
            mapFile2 = Parser.parse(filePath2, file2, ext2);
        } catch (JsonProcessingException e) {
            System.out.println(ext1 + " format error in the file '" + filePath2 + "'");
            System.out.println(e.getMessage());
            return "";
        }

        Set<String> keys = new TreeSet<>(mapFile1.keySet());
        keys.addAll(mapFile2.keySet());

        var diffMap = Comparator.getDiff(keys, mapFile1, mapFile2);
        var result = Formatter.getFormattedOutput(diffMap, format);
        return result;
    }

    public static String generate(String filePath1, String filePath2) {
        return generate(filePath1, filePath2, "stylish");
    }
}
