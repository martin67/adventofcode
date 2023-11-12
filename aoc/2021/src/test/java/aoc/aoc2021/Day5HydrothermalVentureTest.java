package aoc.aoc2021;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2021: Day 5: Hydrothermal Venture")
class Day5HydrothermalVentureTest {

    @ParameterizedTest
    @CsvSource({"5, day5-demo1.txt",
            "5608, day5.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day5HydrothermalVenture(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"12, day5-demo1.txt",
            "20299, day5.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day5HydrothermalVenture(inputLines).problem2()).isEqualTo(expected);
    }
}