package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 9: Rope Bridge")
class Day9RopeBridgeTest {

    @ParameterizedTest
    @CsvSource({"13, day9-demo1.txt",
            "6354, day9.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day9RopeBridge(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1, day9-demo1.txt",
            "36, day9-demo2.txt",
            "2651, day9.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day9RopeBridge(inputLines).problem2()).isEqualTo(expected);
    }
}