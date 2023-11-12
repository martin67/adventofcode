package aoc.aoc2016;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 15: Timing is Everything")
class Day15TimingIsEverythingTest {

    @ParameterizedTest
    @CsvSource({"5, day15-demo1.txt",
            "16824, day15.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day15TimingIsEverything(inputLines).firstTime()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"3543984, day15-2.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day15TimingIsEverything(inputLines).firstTime()).isEqualTo(expected);
    }
}