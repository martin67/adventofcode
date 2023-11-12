package aoc.aoc2021;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2021: Day 12: Passage Pathing")
class Day12PassagePathingTest {

    @ParameterizedTest
    @CsvSource({"10, day12-demo1.txt",
            "19, day12-demo2.txt",
            "226, day12-demo3.txt",
            "3410, day12.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12PassagePathing(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"36, day12-demo1.txt",
            "103, day12-demo2.txt",
            "3509, day12-demo3.txt",
            "98796, day12.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12PassagePathing(inputLines).problem2()).isEqualTo(expected);
    }
}