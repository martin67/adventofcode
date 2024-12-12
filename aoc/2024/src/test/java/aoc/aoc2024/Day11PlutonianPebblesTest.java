package aoc.aoc2024;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2024: Day 11: Plutonian Pebbles")
class Day11PlutonianPebblesTest {

    @ParameterizedTest
    @CsvSource({"55312, day11-demo1.txt",
            "213625, day11.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day11PlutonianPebbles(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"252442982856820, day11.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day11PlutonianPebbles(inputLines).problem2()).isEqualTo(expected);
    }
}