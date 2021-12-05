package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2015: Day 9: All in a Single Night")
class Day9AllInASingleNightTest {

    @ParameterizedTest
    @CsvSource({"605, src/test/resources/2015/day9-demo1.txt",
            "141, src/test/resources/2015/day9.txt"})
    void shortestRoute(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day9AllInASingleNight(inputLines).shortestRoute());
    }

    @ParameterizedTest
    @CsvSource({"982, src/test/resources/2015/day9-demo1.txt",
            "736, src/test/resources/2015/day9.txt"})
    void longestRoute(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day9AllInASingleNight(inputLines).longestRoute());
    }
}