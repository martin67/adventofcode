package aoc.aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2023: Day 14: Parabolic Reflector Dish")
class Day14ParabolicReflectorDishTest {

    @ParameterizedTest
    @CsvSource({"136, day14-demo1.txt",
            "0, day14.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day14ParabolicReflectorDish(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"0, day14-demo1.txt",
            "0, day14.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day14ParabolicReflectorDish(inputLines).problem2()).isEqualTo(expected);
    }
}