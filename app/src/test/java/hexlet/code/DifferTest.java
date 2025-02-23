package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    private static String resultStylish;

    @BeforeAll
    public static void generateResult() throws Exception {
        resultStylish = readFixture("resultStylish.txt");
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
        var actual = Differ.generate(filePath1, filePath2);
        assertEquals(resultStylish, actual);
    }

    @Test
    public void testMethodThrowsIOException() throws IOException {
        var filePath1 = "src/test/resources/fixtures/no_file.json";
        var expected = "Check the file '" + filePath1 + "' exist and the access.";
        var thrownMessage = Differ.generate(filePath1, filePath1);

        assertEquals(expected, thrownMessage);
    }

    @Test
    public void testMethodThrowsJsonProcessingException() throws IOException, JsonProcessingException {
        var filePath1 = "src/test/resources/fixtures/file_no_valid.json";
        var expected = "Json format error in the file '" + filePath1 + "'";
        var thrownMessage = Differ.generate(filePath1, filePath1);

        assertEquals(expected, thrownMessage);
    }
}
