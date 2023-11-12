package aoc.aoc2016;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 18: Like a Rogue")
class Day18LikeARogueTest {

    @ParameterizedTest
    @CsvSource({"38, 10, day18-demo1.txt",
            "2035, 40, day18.txt"})
    void problem1(long expected, int rows, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18LikeARogue(inputLines).safeTiles(rows)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"20000577, 400000, day18.txt"})
    void problem2(long expected, int rows, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18LikeARogue(inputLines).safeTiles(rows)).isEqualTo(expected);
    }
}