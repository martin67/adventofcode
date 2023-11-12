package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 5: Binary Boarding")
class Day5BinaryBoardingTest {

    @ParameterizedTest
    @CsvSource({"820, day5-demo1.txt",
            "911, day5.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day5BinaryBoarding(inputLines).highestId()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"629, day5.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day5BinaryBoarding(inputLines).myId()).isEqualTo(expected);
    }
}
