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
    private static Path getPath(String filePath) throws InvalidPathException {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    private static String readFile(String filePath) throws IOException {
        Path path = getPath(filePath);
        return new String(Files.readString(path).trim());
    }

    public static String getFileExtension(String filePath) {
        int index = filePath.lastIndexOf('.');
        return filePath.substring(index + 1);
    }

    public static String generate(String filePath1, String filePath2, String format) throws IOException {
        String file1 = readFile(filePath1);
        String file2 = readFile(filePath2);

        var ext1 = getFileExtension(filePath1);
        var ext2 = getFileExtension(filePath2);

        Map<String, Object> mapFile1 = Parser.parse(file1, ext1);
        Map<String, Object> mapFile2 = Parser.parse(file2, ext2);

        Set<String> keys = new TreeSet<>(mapFile1.keySet());
        keys.addAll(mapFile2.keySet());

        var diffMap = Comparator.getDiff(keys, mapFile1, mapFile2);
        var result = Formatter.getFormattedOutput(diffMap, format);
        return result;
    }

    public static String generate(String filePath1, String filePath2) throws IOException {
        return generate(filePath1, filePath2, "stylish");
    }
}
