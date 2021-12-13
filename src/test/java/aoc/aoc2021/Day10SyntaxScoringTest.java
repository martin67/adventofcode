package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 10: Syntax Scoring")
class Day10SyntaxScoringTest {

    @ParameterizedTest
    @CsvSource({"26397, src/test/resources/2021/day10-demo1.txt",
            "367227, src/test/resources/2021/day10.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day10SyntaxScoring(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"288957, src/test/resources/2021/day10-demo1.txt",
            "3583341858, src/test/resources/2021/day10.txt"})
    void problem2(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day10SyntaxScoring(inputLines).problem2());
    }

}