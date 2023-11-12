package aoc.aoc2015;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 15: Science for Hungry People")
class Day15ScienceForHungryPeopleTest {

    @ParameterizedTest
    @CsvSource({"62842880, day15-demo1.txt",
            "21367368, day15.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day15ScienceForHungryPeople(inputLines).totalScore(false)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"57600000, day15-demo1.txt",
            "1766400, day15.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day15ScienceForHungryPeople(inputLines).totalScore(true)).isEqualTo(expected);
    }

}