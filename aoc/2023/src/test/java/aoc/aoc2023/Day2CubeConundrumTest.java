package aoc.aoc2023;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("2023: Day 2: Cube Conundrum")
class Day2CubeConundrumTest {

    @ParameterizedTest
    @CsvSource({"8, day2-demo1.txt",
            "2720, day2.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day2CubeConundrum(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2286, day2-demo1.txt",
            "71535, day2.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day2CubeConundrum(inputLines).problem2()).isEqualTo(expected);
    }
}