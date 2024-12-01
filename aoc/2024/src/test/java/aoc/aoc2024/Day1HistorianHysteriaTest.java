package aoc.aoc2024;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2024: Day 1: Historian Hysteria")
class Day1HistorianHysteriaTest {

    @ParameterizedTest
    @CsvSource({"11, day1-demo1.txt",
            "765748, day1.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day1HistorianHysteria(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"31, day1-demo1.txt",
            "27732508, day1.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day1HistorianHysteria(inputLines).problem2()).isEqualTo(expected);
    }
}