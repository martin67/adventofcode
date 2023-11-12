package aoc.aoc2021;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2021: Day 8: Seven Segment Search")
class Day8SevenSegmentSearchTest {

    @ParameterizedTest
    @CsvSource({"0, day8-demo1.txt",
            "26, day8-demo2.txt",
            "548, day8.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day8SevenSegmentSearch(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"5353, day8-demo1.txt",
            "61229, day8-demo2.txt",
            "1074888, day8.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day8SevenSegmentSearch(inputLines).problem2()).isEqualTo(expected);
    }
}