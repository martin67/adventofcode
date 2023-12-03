package aoc.aoc2023;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("2023: Day 3: Gear Ratios")
class Day3GearRatiosTest {

    @ParameterizedTest
    @CsvSource({"4361, day3-demo1.txt",
            "525911, day3.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day3GearRatios(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"467835, day3-demo1.txt",
            "75805607, day3.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day3GearRatios(inputLines).problem2()).isEqualTo(expected);
    }
}