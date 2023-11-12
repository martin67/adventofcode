package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 20: Donut Maze")
class Day20DonutMazeTest {

    @ParameterizedTest
    @CsvSource({"23, day20-demo1.txt",
            "58, day20-demo2.txt",
            "442, day20.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day20DonutMaze(inputLines).shortestPath()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"396, day20-demo3.txt",
            "5208, day20.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day20DonutMaze(inputLines).shortestRecursivePath()).isEqualTo(expected);
    }
}