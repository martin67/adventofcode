package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 17: Conway Cubes")
class Day17ConwayCubesTest {

    @ParameterizedTest
    @CsvSource({"112, day17-demo1.txt",
            "255, day17.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day17ConwayCubes(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"848, day17-demo1.txt",
            "2340, day17.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day17ConwayCubes(inputLines).problem2()).isEqualTo(expected);
    }
}
