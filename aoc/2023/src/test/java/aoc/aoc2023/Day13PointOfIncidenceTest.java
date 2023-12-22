package aoc.aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2023: Day 13: Point of Incidence")
class Day13PointOfIncidenceTest {

    @ParameterizedTest
    @CsvSource({"405, day13-demo1.txt",
            "0, day13.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day13PointOfIncidence(inputLines).problem1()).isEqualTo(expected);
    }

    // 29770 too low

    @ParameterizedTest
    @CsvSource({"0, day13-demo1.txt",
            "0, day13.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day13PointOfIncidence(inputLines).problem2()).isEqualTo(expected);
    }
}