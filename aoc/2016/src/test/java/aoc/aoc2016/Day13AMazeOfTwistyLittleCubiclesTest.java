package aoc.aoc2016;

import aoc.common.Position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 13: A Maze of Twisty Little Cubicles")
class Day13AMazeOfTwistyLittleCubiclesTest {

    @ParameterizedTest
    @CsvSource({"11, 10, 7, 4",
            "86, 1364, 31, 39"})
    void problem1(int expected, int favoriteNumber, int x, int y) {
        assertThat(new Day13AMazeOfTwistyLittleCubicles(favoriteNumber).fewestNumberOfSteps(new Position(x, y)))
                .isEqualTo(expected);
    }

    @Test
    void problem2() {
        assertThat(new Day13AMazeOfTwistyLittleCubicles(1364).numberOfLocations()).isEqualTo(127);
    }
}