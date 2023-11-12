package aoc.aoc2016;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 23: Safe Cracking")
class Day23SafeCrackingTest {

    @ParameterizedTest
    @CsvSource({"3, day23-demo1.txt",
            "12703, day23.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day23SafeCracking(inputLines).safeValue()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"479009263, day23.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day23SafeCracking(inputLines).secondValue()).isEqualTo(expected);
    }
}