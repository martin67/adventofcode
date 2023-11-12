package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 10: Cathode-Ray Tube")
class Day10CathodeRayTubeTest {

    @ParameterizedTest
    @CsvSource({"13140, day10-demo1.txt",
            "15360, day10.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day10CathodeRayTube(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"605, day10-demo1.txt",
            "-281, day10.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day10CathodeRayTube(inputLines).problem2()).isEqualTo(expected);
    }
}