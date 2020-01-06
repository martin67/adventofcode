package aoc.aoc2018;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class Day9MarbleManiaTest {

    @ParameterizedTest
    @CsvSource({
            "9, 25, 32",
            "10, 1618, 8317",
            "13, 7999, 146373",
            "17, 1104, 2764",
            "21, 6111, 54718",
            "30, 5807, 37305",
            "424, 71144, 405143",
            "424, 7114400, 3411514667"
    })
    void computeScore(int players, int points, long expected) {
        Day9MarbleMania day9MarbleMania = new Day9MarbleMania();
        assertEquals(expected, day9MarbleMania.computeScore(players, points));
    }

}