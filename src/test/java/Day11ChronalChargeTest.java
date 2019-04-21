import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class Day11ChronalChargeTest {

    @ParameterizedTest
    @CsvSource({
            "122,79, 57, -5",
            "217,196, 39, 0",
            "101,153, 71, 4"
    })
    void checkPowerLevel(int x, int y, int serial, int powerLevel) {
        Day11ChronalCharge.Coordinate coordinate = new Day11ChronalCharge.Coordinate(x,y);
        Day11ChronalCharge day11ChronalCharge = new Day11ChronalCharge();
        int result = day11ChronalCharge.getPowerLevel(coordinate, serial);
        assertEquals(powerLevel, result);
    }

    @ParameterizedTest
    @CsvSource({
            "18, '33,45'",
            "42, '21,61'",
            "9445, '233,36'"
    })
    void findGrid(int serial, String coord) {
        Day11ChronalCharge day11ChronalCharge = new Day11ChronalCharge();
        Day11ChronalCharge.Coordinate answer = day11ChronalCharge.findLargestTotalPower(serial);
        assertEquals(coord, answer.toString());
    }
}