package aoc.aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2023: Day 18: Lavaduct Lagoon")
class Day18LavaductLagoonTest {

    @ParameterizedTest
    @CsvSource({"62, day18-demo1.txt",
            "0, day18.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18LavaductLagoon(inputLines).problem1()).isEqualTo(expected);
    }
// 93240 too high
// 87739 too low
// 4072 too low
    @ParameterizedTest
    @CsvSource({"0, day18-demo1.txt",
            "0, day18.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18LavaductLagoon(inputLines).problem2()).isEqualTo(expected);
    }
}