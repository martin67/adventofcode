package aoc.aoc2016;

import aoc.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13AMazeOfTwistyLittleCubiclesTest {

    @ParameterizedTest
    @CsvSource({"11, 10, 7, 4",
            "86, 1364, 31, 39"})
    void fewestNumberOfSteps(int expected, int favoriteNumber, int x, int y) {
        assertEquals(expected, new Day13AMazeOfTwistyLittleCubicles(favoriteNumber).fewestNumberOfSteps(new Position(x, y)));
    }
}