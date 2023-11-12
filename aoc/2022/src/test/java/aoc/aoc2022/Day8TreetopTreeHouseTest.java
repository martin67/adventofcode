package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 8: Treetop Tree House")
class Day8TreetopTreeHouseTest {

    @ParameterizedTest
    @CsvSource({"21, day8-demo1.txt",
            "1782, day8.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day8TreetopTreeHouse(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"8, day8-demo1.txt",
            "474606, day8.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day8TreetopTreeHouse(inputLines).problem2()).isEqualTo(expected);
    }
}