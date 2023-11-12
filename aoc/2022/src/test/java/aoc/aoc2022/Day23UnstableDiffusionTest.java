package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 23: UnstableDiffusion")
class Day23UnstableDiffusionTest {

    @ParameterizedTest
    @CsvSource({"110, day23-demo1.txt",
            "3864, day23.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day23UnstableDiffusion(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"20, day23-demo1.txt",
            "946, day23.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day23UnstableDiffusion(inputLines).problem2()).isEqualTo(expected);
    }
}