package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DifferTest {
    private static String resultStylish;
    private static String resultPlain;
    private static String resultJson;

    @BeforeAll
    public static void generateResult() throws Exception {
        resultStylish = readFixture("resultStylish.txt");
        resultPlain = readFixture("resultPlain.txt");
        resultJson = readFixture("resultJson.json");
    }

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    private static String readFixture(String fileName) throws IOException {
        var path = getFixturePath(fileName);
        return Files.readString(path).trim();
    }

    private static Stream<Arguments> argsFilesFactory() {
        return Stream.of(
                Arguments.of("src/test/resources/fixtures/file1.json", "src/test/resources/fixtures/file2.json"),
                Arguments.of("src/test/resources/fixtures/file1.yaml", "src/test/resources/fixtures/file2.yaml")
        );
    }

    @ParameterizedTest
    @MethodSource("argsFilesFactory")
    public void testGenerateWithoutFormat(String filePath1, String filePath2) throws IOException {
        var actual = Differ.generate(filePath1, filePath2);
        assertEquals(resultStylish, actual);
    }

    @ParameterizedTest
    @MethodSource("argsFilesFactory")
    public void testGenerateStylishFormat(String filePath1, String filePath2) throws IOException {
        var actual = Differ.generate(filePath1, filePath2, "stylish");
        assertEquals(resultStylish, actual);
    }

    @ParameterizedTest
    @MethodSource("argsFilesFactory")
    public void testGeneratePlainFormat(String filePath1, String filePath2) throws IOException {
        var actual = Differ.generate(filePath1, filePath2, "plain");
        assertEquals(resultPlain, actual);
    }
    @ParameterizedTest
    @MethodSource("argsFilesFactory")
    public void testGenerateJsonFormat(String filePath1, String filePath2) throws IOException {
        var actual = Differ.generate(filePath1, filePath2, "json");
        assertEquals(resultJson, actual);
    }

    @Test
    public void testGenerateIncorrectFileFormat() {
        var filePath1 = "src/test/resources/fixtures/file_txt_format.txt";
        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            Differ.generate(filePath1, filePath1, "stylish");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void testGenerateIncorrectOutputFormat() {
        var filePath1 = "src/test/resources/fixtures/file1.json";
        var filePath2 = "src/test/resources/fixtures/file2.json";
        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            Differ.generate(filePath1, filePath2, "no_format");
        });
        assertNotNull(thrown.getMessage());
    }
}
