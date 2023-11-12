package aoc.aoc2015;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 8: Matchsticks")
class Day8MatchsticksTest {
    @ParameterizedTest
    @CsvSource({"12, day8-demo1.txt",
            "1342, day8.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day8Matchsticks(inputLines).matches()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"19, day8-demo1.txt",
            "2074, day8.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day8Matchsticks(inputLines).encoded()).isEqualTo(expected);
    }
}