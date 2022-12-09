package aoc.aoc2018;

import aoc.common.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2018: Day 11: Chronal Charge")
class Day11ChronalChargeTest {

    @ParameterizedTest
    @CsvSource({
            "3,5, 8, 4",
            "122,79, 57, -5",
            "217,196, 39, 0",
            "101,153, 71, 4"
    })
    void checkPowerLevel(int x, int y, int serial, int powerLevel) {
        Position position = new Position(x, y);
        Day11ChronalCharge day11ChronalCharge = new Day11ChronalCharge();
        int result = day11ChronalCharge.getPowerLevel(position, serial);
        assertEquals(powerLevel, result);
    }

    @ParameterizedTest
    @CsvSource({
            "18, '33,45'",
            "42, '21,61'",
            "9445, '233,36'"
    })
    void problem1(int serial, String coord) {
        Day11ChronalCharge day11ChronalCharge = new Day11ChronalCharge();
        Position answer = day11ChronalCharge.findLargestTotalPower(serial);
        assertEquals(coord, answer.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "18, '90,269,16'",
            "42, '232,251,12'",
            "9445, '231,107,14'"
    })
    void problem2(int serial, String expected) {
        Day11ChronalCharge day11ChronalCharge = new Day11ChronalCharge();
        String answer = day11ChronalCharge.findLargestTotalPowerAnySize(serial);
        assertEquals(expected, answer);
    }

}