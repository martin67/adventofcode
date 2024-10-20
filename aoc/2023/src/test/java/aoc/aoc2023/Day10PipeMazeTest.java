package aoc.aoc2023;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("2023: Day 10: Pipe Maze")
class Day10PipeMazeTest {

    @ParameterizedTest
    @CsvSource({"4, day10-demo1.txt",
            "8, day10-demo2.txt",
            "6890, day10.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day10PipeMaze(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"4, day10-demo3.txt",
            "4, day10-demo4.txt",
            "8, day10-demo5.txt",
            "10, day10-demo6.txt",
            "453, day10.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day10PipeMaze(inputLines).problem2()).isEqualTo(expected);
    }
}