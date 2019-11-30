package aoc2018;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day24ImmuneSystemSimulator20XXTest {

    @ParameterizedTest
    @CsvSource({"5216, src/test/resources/2018/day24-demo0.txt",
            "15493, src/test/resources/2018/day24.txt"})
    void winningArmyUnits(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day24ImmuneSystemSimulator20XX(fileName).winningArmyUnits());
    }

    @ParameterizedTest
    @CsvSource({"51, src/test/resources/2018/day24-demo0.txt",
            "1045, src/test/resources/2018/day24.txt"})
    void immuneSystemUnitsLeft(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day24ImmuneSystemSimulator20XX(fileName).immuneSystemUnitsLeft());
    }
}