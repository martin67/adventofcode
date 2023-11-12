package aoc.aoc2021;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2021: Day 3: Binary Diagnostic")
class Day3BinaryDiagnosticTest {

    @ParameterizedTest
    @CsvSource({"198, day3-demo1.txt",
            "3895776, day3.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day3BinaryDiagnostic(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"230, day3-demo1.txt",
            "7928162, day3.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day3BinaryDiagnostic(inputLines).problem2()).isEqualTo(expected);
    }
}