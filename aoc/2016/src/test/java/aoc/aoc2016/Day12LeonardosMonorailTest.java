package aoc.aoc2016;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 12: Leonardo's Monorail")
class Day12LeonardosMonorailTest {

    @ParameterizedTest
    @CsvSource({"42, day12-demo1.txt",
            "318020, day12.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12LeonardosMonorail(inputLines).registerA()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"9227674, day12.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12LeonardosMonorail(inputLines).registerAwithC()).isEqualTo(expected);
    }
}