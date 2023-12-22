package aoc.aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2023: Day 19: Aplenty")
class Day19AplentyTest {

    @ParameterizedTest
    @CsvSource({"19114, day19-demo1.txt",
            "487623, day19.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day19Aplenty(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"167409079868000, day19-demo1.txt",
            "0, day19.txt"})
    void problem2(String expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day19Aplenty(inputLines).problem2()).isEqualTo(expected);
    }
}