package aoc.aoc2015;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 9: All in a Single Night")
class Day9AllInASingleNightTest {

    @ParameterizedTest
    @CsvSource({"605, day9-demo1.txt",
            "141, day9.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day9AllInASingleNight(inputLines).shortestRoute()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"982, day9-demo1.txt",
            "736, day9.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day9AllInASingleNight(inputLines).longestRoute()).isEqualTo(expected);
    }
}