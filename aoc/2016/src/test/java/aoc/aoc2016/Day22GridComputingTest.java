package aoc.aoc2016;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 22: Grid Computing")
class Day22GridComputingTest {

    @ParameterizedTest
    @CsvSource({"7, day22-demo1.txt",
            "934, day22.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day22GridComputing(inputLines).viableNodes()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"7, day22-demo1.txt",
            "207, day22.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day22GridComputing(inputLines).fewestSteps()).isEqualTo(expected);
    }
}