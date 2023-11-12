package aoc.aoc2021;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2021: Day 10: Syntax Scoring")
class Day10SyntaxScoringTest {

    @ParameterizedTest
    @CsvSource({"26397, day10-demo1.txt",
            "367227, day10.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day10SyntaxScoring(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"288957, day10-demo1.txt",
            "3583341858, day10.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day10SyntaxScoring(inputLines).problem2()).isEqualTo(expected);
    }
}