package aoc.aoc2015;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 13: Knights of the Dinner Table")
class Day13KnightsOfTheDinnerTableTest {

    @ParameterizedTest
    @CsvSource({"330, day13-demo1.txt",
            "664, day13.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day13KnightsOfTheDinnerTable(inputLines).changeInHappiness()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"286, day13-demo1.txt",
            "640, day13.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day13KnightsOfTheDinnerTable(inputLines).changeInHappinessWithMe()).isEqualTo(expected);
    }

}