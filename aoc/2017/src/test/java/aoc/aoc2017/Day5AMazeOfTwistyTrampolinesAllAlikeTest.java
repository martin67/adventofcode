package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 5: A Maze of Twisty Trampolines, All Alike")
class Day5AMazeOfTwistyTrampolinesAllAlikeTest {

    @ParameterizedTest
    @CsvSource({"5, day5-demo1.txt",
            "360603, day5.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertThat(new Day5AMazeOfTwistyTrampolinesAllAlike(AocFiles.readAllLines(fileName)).countSteps())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"10, day5-demo1.txt",
            "25347697, day5.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertThat(new Day5AMazeOfTwistyTrampolinesAllAlike(AocFiles.readAllLines(fileName)).countStepsBackwards())
                .isEqualTo(expected);
    }
}