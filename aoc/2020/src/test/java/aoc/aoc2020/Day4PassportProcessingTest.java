package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 4: Passport Processing")
class Day4PassportProcessingTest {

    @ParameterizedTest
    @CsvSource({"2, day4-demo1.txt",
            "219, day4.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day4PassportProcessing(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"0, day4-demo2.txt",
            "4, day4-demo3.txt",
            "127, day4.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day4PassportProcessing(inputLines).problem2()).isEqualTo(expected);
    }
}
