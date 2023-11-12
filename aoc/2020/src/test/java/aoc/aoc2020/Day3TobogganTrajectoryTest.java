package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 3: Toboggan Trajectory")
class Day3TobogganTrajectoryTest {

    @ParameterizedTest
    @CsvSource({"7, day3-demo1.txt",
            "176, day3.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day3TobogganTrajectory(inputLines).treesEncountered(3, 1)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"336, day3-demo1.txt",
            "5872458240, day3.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day3TobogganTrajectory(inputLines).treesSecondEncounter()).isEqualTo(expected);
    }
}