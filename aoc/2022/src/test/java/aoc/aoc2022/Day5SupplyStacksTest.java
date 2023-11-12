package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 5: Supply Stacks")
class Day5SupplyStacksTest {

    @ParameterizedTest
    @CsvSource({"CMZ, day5-demo1.txt",
            "CNSZFDVLJ, day5.txt"})
    void problem1(String expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day5SupplyStacks(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"MCD, day5-demo1.txt",
            "QNDWLMGNS, day5.txt"})
    void problem2(String expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day5SupplyStacks(inputLines).problem2()).isEqualTo(expected);
    }
}