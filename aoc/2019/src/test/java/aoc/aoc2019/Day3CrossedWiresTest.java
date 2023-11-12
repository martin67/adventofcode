package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 3: Crossed Wires")
class Day3CrossedWiresTest {

    @ParameterizedTest
    @CsvSource({"6, day3-demo1.txt",
            "159, day3-demo2.txt",
            "135, day3-demo3.txt",
            "865, day3.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day3CrossedWires(inputLines).distanceToClosestIntersection()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"30, day3-demo1.txt",
            "610, day3-demo2.txt",
            "410, day3-demo3.txt",
            "35038, day3.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day3CrossedWires(inputLines).stepsToClosestIntersection()).isEqualTo(expected);
    }
}