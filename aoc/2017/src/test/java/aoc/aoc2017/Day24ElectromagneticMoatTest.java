package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 24: Electromagnetic Moat")
class Day24ElectromagneticMoatTest {

    @ParameterizedTest
    @CsvSource({"31, day24-demo1.txt",
            "1940, day24.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day24ElectromagneticMoat(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"19, day24-demo1.txt",
            "1928, day24.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day24ElectromagneticMoat(inputLines).problem2()).isEqualTo(expected);
    }
}