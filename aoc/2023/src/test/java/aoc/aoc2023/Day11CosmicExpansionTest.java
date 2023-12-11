package aoc.aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2023: Day 11: Cosmic Expansion")
class Day11CosmicExpansionTest {

    @ParameterizedTest
    @CsvSource({"374, day11-demo1.txt",
            "10313550, day11.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day11CosmicExpansion(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1030, 10, day11-demo1.txt",
            "8410, 100, day11-demo1.txt",
            "611998089572, 1000000, day11.txt"})
    void problem2(long expected, int size, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day11CosmicExpansion(inputLines).problem2(size)).isEqualTo(expected);
    }
}