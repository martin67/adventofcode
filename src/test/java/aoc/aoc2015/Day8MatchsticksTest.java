package aoc.aoc2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8MatchsticksTest {
    @ParameterizedTest
    @CsvSource({"12, src/test/resources/2015/day8-demo1.txt",
            "1342, src/test/resources/2015/day8.txt"})
    void matches(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day8Matchsticks(inputLines).matches());
    }

    @ParameterizedTest
    @CsvSource({"19, src/test/resources/2015/day8-demo1.txt",
            "2074, src/test/resources/2015/day8.txt"})
    void encoded(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day8Matchsticks(inputLines).encoded());
    }
}