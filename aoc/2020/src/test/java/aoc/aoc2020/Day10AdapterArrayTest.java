package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 10: Adapter Array")
class Day10AdapterArrayTest {

    @ParameterizedTest
    @CsvSource({"35, day10-demo1.txt",
            "220, day10-demo2.txt",
            "2210, day10.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day10AdapterArray(inputLines).joltDifference()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"8, day10-demo1.txt",
            "19208, day10-demo2.txt",
            "7086739046912, day10.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day10AdapterArray(inputLines).adapterCombinations2()).isEqualTo(expected);
    }
}
