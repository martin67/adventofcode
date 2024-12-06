package aoc.aoc2024;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2024: Day 6: Guard Gallivant")
class Day6GuardGallivantTest {

    @ParameterizedTest
    @CsvSource({"41, day6-demo1.txt",
            "4515, day6.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day6GuardGallivant(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"6, day6-demo1.txt",
            "1309, day6.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day6GuardGallivant(inputLines).problem2()).isEqualTo(expected);
    }
}