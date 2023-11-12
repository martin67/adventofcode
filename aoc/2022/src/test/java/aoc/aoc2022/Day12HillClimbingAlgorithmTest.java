package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 12: Hill Climbing Algorithm")
class Day12HillClimbingAlgorithmTest {

    @ParameterizedTest
    @CsvSource({"31, day12-demo1.txt",
            "391, day12.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12HillClimbingAlgorithm(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"29, day12-demo1.txt",
            "386, day12.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12HillClimbingAlgorithm(inputLines).problem2()).isEqualTo(expected);
    }
}