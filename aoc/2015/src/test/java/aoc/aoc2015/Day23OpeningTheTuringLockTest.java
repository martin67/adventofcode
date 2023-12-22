package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 23: Opening the Turing Lock")
class Day23OpeningTheTuringLockTest {

    @ParameterizedTest
    @CsvSource({"0, day23-demo1.txt",
            "0, day23.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day23OpeningTheTuringLock(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"0, day23-demo1.txt",
            "0, day23.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day23OpeningTheTuringLock(inputLines).problem2()).isEqualTo(expected);
    }
}
