package aoc.aoc2023;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("2023: Day 24: Never Tell Me The Odds")
class Day24NeverTellMeTheOddsTest {

    @ParameterizedTest
    @CsvSource({"0, day24-demo1.txt",
            "0, day24.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day24NeverTellMeTheOdds(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"0, day24-demo1.txt",
            "0, day24.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day24NeverTellMeTheOdds(inputLines).problem2()).isEqualTo(expected);
    }
}