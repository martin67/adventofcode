package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 18: Boiling Boulders")
class Day18BoilingBouldersTest {

    @ParameterizedTest
    @CsvSource({"10, day18-demo1.txt",
            "64, day18-demo2.txt",
            "4636, day18.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18BoilingBoulders(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"58, day18-demo2.txt",
            "2572, day18.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18BoilingBoulders(inputLines).problem2()).isEqualTo(expected);
    }
}