package aoc.aoc2021;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2021: Day 14: Extended Polymerization")
class Day14ExtendedPolymerizationTest {

    @ParameterizedTest
    @CsvSource({"1588, day14-demo1.txt",
            "2170, day14.txt"})
    void problem1(int expected, String fileName) throws IOException {
       var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day14ExtendedPolymerization(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2188189693529, day14-demo1.txt",
            "2422444761283, day14.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day14ExtendedPolymerization(inputLines).problem2()).isEqualTo(expected);
    }
}