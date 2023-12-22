package aoc.aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2023: Day 21: Step Counter")
class Day21StepCounterTest {

    @ParameterizedTest
    @CsvSource({"16, 6, day21-demo1.txt",
            "3795, 64, day21.txt"})
    void problem1(int expected, int steps, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day21StepCounter(inputLines).problem1(steps)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"16, 6, day21-demo1.txt",
            "50, 10, day21-demo1.txt",
            "1594, 50, day21-demo1.txt",
            "6536, 100, day21-demo1.txt",
            "167004, 500, day21-demo1.txt",
            "668697, 1000, day21-demo1.txt",
            "16733044, 5000, day21-demo1.txt",
            "0, 26501365, day21.txt"})
    void problem2(int expected, int steps, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day21StepCounter(inputLines).problem2(steps)).isEqualTo(expected);
    }
}