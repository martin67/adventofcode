package aoc.aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2023: Day 8: Haunted Wasteland")
class Day8HauntedWastelandTest {

    @ParameterizedTest
    @CsvSource({"2, day8-demo1.txt",
            "6, day8-demo2.txt",
            "12083, day8.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day8HauntedWasteland(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"6, day8-demo3.txt",
            "13385272668829, day8.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day8HauntedWasteland(inputLines).problem2()).isEqualTo(expected);
    }
}