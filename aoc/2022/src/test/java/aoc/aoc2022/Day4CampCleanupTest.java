package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 4: Camp Cleanup")
class Day4CampCleanupTest {

    @ParameterizedTest
    @CsvSource({"2, day4-demo1.txt",
            "532, day4.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day4CampCleanup(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"4, day4-demo1.txt",
            "854, day4.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day4CampCleanup(inputLines).problem2()).isEqualTo(expected);
    }
}