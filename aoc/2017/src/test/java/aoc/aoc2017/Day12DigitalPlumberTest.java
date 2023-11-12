package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 12: Digital Plumber")
class Day12DigitalPlumberTest {

    @ParameterizedTest
    @CsvSource({"6, day12-demo1.txt",
            "175, day12.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12DigitalPlumber(inputLines).programsInGroup()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2, day12-demo1.txt",
            "213, day12.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12DigitalPlumber(inputLines).numberOfGroups()).isEqualTo(expected);
    }
}