package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 19: Not Enough Minerals")
class Day19NotEnoughMineralsTest {

    @ParameterizedTest
    @CsvSource({"33, day19-demo1.txt",
            "7763, day19.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day19NotEnoughMinerals(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"70, day19-demo1.txt",
            "2569, day19.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day19NotEnoughMinerals(inputLines).problem2()).isEqualTo(expected);
    }
}