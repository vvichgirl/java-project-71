package hexlet.code;

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

    private static String readFile(String filePath) {
        Path path = getPath(filePath);
        try {
            return new String(Files.readString(path).trim());
        } catch (IOException e) {
            System.out.println("Check the file '" + filePath + "' exist and the access.");
            return "";
        }
    }

    public static String getFileExtension(String filePath) {
        int index = filePath.lastIndexOf('.');
        return filePath.substring(index + 1);
    }

    public static String generate(String filePath1, String filePath2, String format) {
        String file1 = readFile(filePath1);
        String file2 = readFile(filePath2);

        var ext1 = getFileExtension(filePath1);
        var ext2 = getFileExtension(filePath2);

        Map<String, String> mapFile1 = Parser.parse(filePath1, file1, ext1);
        Map<String, String> mapFile2 = Parser.parse(filePath2, file2, ext2);

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
