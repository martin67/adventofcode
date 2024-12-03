package aoc.aoc2024;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2024: Day 3: Mull It Over")
class Day3MullItOverTest {

    @ParameterizedTest
    @CsvSource({"161, day3-demo1.txt",
            "187833789, day3.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day3MullItOver(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"48, day3-demo2.txt",
            "94455185, day3.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day3MullItOver(inputLines).problem2()).isEqualTo(expected);
    }
}