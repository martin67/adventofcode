package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2016: Day 18: Like a Rogue")
class Day18LikeARogueTest {

    @ParameterizedTest
    @CsvSource({"38, 10, src/test/resources/day18-demo1.txt",
            "2035, 40, src/test/resources/day18.txt"})
    void problem1(long expected, int rows, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18LikeARogue(inputLines).safeTiles(rows));
    }

    @ParameterizedTest
    @CsvSource({"20000577, 400000, src/test/resources/day18.txt"})
    void problem2(long expected, int rows, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18LikeARogue(inputLines).safeTiles(rows));
    }

}