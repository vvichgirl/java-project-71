package hexlet.code;

//import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DifferTest {
    private static String resultStylish;
    private static String resultPlain;

    @BeforeAll
    public static void generateResult() throws Exception {
        resultStylish = readFixture("resultStylish.txt");
        resultPlain = readFixture("resultPlain.txt");
    }

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    private static String readFixture(String fileName) throws IOException {
        var path = getFixturePath(fileName);
        return Files.readString(path).trim();
    }

    @Test
    public void testGenerateJsonStylish() throws IOException {
        var filePath1 = "src/test/resources/fixtures/file1.json";
        var filePath2 = "src/test/resources/fixtures/file2.json";
        var actual = Differ.generate(filePath1, filePath2, "stylish");
        assertEquals(resultStylish, actual);
    }

    @Test
    public void testGenerateJsonWithoutFormat() throws IOException {
        var filePath1 = "src/test/resources/fixtures/file1.json";
        var filePath2 = "src/test/resources/fixtures/file2.json";
        var actual = Differ.generate(filePath1, filePath2);
        assertEquals(resultStylish, actual);
    }

    @Test
    public void testGenerateYAMLStylish() throws IOException {
        var filePath1 = "src/test/resources/fixtures/file1.yaml";
        var filePath2 = "src/test/resources/fixtures/file2.yaml";
        var actual = Differ.generate(filePath1, filePath2, "stylish");
        assertEquals(resultStylish, actual);
    }

    @Test
    public void testGenerateJsonPlain() throws IOException {
        var filePath1 = "src/test/resources/fixtures/file1.json";
        var filePath2 = "src/test/resources/fixtures/file2.json";
        var actual = Differ.generate(filePath1, filePath2, "plain");
        assertEquals(resultPlain, actual);
    }

    @Test
    public void testGenerateIncorrectFileFormat() throws IOException {
        var filePath1 = "src/test/resources/fixtures/file_txt_format.txt";
        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            Differ.generate(filePath1, filePath1, "stylish");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void testGenerateIncorrectOutputFormat() throws IOException {
        var filePath1 = "src/test/resources/fixtures/file1.json";
        var filePath2 = "src/test/resources/fixtures/file2.json";
        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            Differ.generate(filePath1, filePath2, "no_format");
        });
        assertNotNull(thrown.getMessage());
    }
}
