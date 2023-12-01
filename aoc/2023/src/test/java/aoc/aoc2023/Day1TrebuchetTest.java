package aoc.aoc2023;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("2023: Day 1: Trebuchet?!")
class Day1TrebuchetTest {

    @ParameterizedTest
    @CsvSource({"142, day1-demo1.txt",
            "54630, day1.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day1Trebuchet(inputLines, false).problem()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"281, day1-demo2.txt",
            "54770, day1.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day1Trebuchet(inputLines, true).problem()).isEqualTo(expected);
    }
}