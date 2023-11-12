package aoc.aoc2016;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 24: Air Duct Spelunking")
class Day24AirDuctSpelunkingTest {

    @ParameterizedTest
    @CsvSource({"14, day24-demo1.txt",
            "464, day24.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day24AirDuctSpelunking(inputLines).fewestSteps()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"20, day24-demo1.txt",
            "652, day24.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day24AirDuctSpelunking(inputLines).fewestStepsAndReturn()).isEqualTo(expected);
    }
}