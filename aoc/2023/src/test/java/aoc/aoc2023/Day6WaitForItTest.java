package aoc.aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2023: Day 6: Wait For It")
class Day6WaitForItTest {

    @ParameterizedTest
    @CsvSource({"288, day6-demo1.txt",
            "281600, day6.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day6WaitForIt(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"71503, day6-demo1.txt",
            "33875953, day6.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day6WaitForIt(inputLines).problem2()).isEqualTo(expected);
    }
}