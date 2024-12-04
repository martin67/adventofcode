package aoc.aoc2024;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2024: Day 4: Ceres Search")
class Day4CeresSearchTest {

    @ParameterizedTest
    @CsvSource({"18, day4-demo1.txt",
            "2507, day4.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day4CeresSearch(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"9, day4-demo1.txt",
            "1969, day4.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day4CeresSearch(inputLines).problem2()).isEqualTo(expected);
    }
}