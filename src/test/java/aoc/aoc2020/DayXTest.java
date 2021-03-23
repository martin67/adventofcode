package aoc.aoc2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DayXTest {

    @ParameterizedTest
    @CsvSource({"0, src/test/resources/2020/dayX-demo1.txt",
            "0, src/test/resources/2020/dayX.txt"})
    void problem(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new DayX(inputLines).problem());
    }
}