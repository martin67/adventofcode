package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 2: Rock Paper Scissors")
class Day2RockPaperScissorsTest {

    @ParameterizedTest
    @CsvSource({"15, day2-demo1.txt",
            "11873, day2.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day2RockPaperScissors(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"12, day2-demo1.txt",
            "12014, day2.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day2RockPaperScissors(inputLines).problem2()).isEqualTo(expected);
    }
}