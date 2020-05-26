package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day24AirDuctSpelunkingTest {
    @ParameterizedTest
    @CsvSource({"14, src/test/resources/2016/day24-demo1.txt",
            "464, src/test/resources/2016/day24.txt"})
    void fewestSteps(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day24AirDuctSpelunking(inputLines).fewestSteps());
    }
}