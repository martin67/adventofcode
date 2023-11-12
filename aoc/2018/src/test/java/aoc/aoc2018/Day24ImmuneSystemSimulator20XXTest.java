package aoc.aoc2018;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 24: Immune System Simulator 20XX")
class Day24ImmuneSystemSimulator20XXTest {

    @ParameterizedTest
    @CsvSource({"5216, src/test/resources/day24-demo1.txt",
            "15493, src/test/resources/day24.txt"})
    @Disabled
    void problem1(int expected, String fileName) throws IOException {
        assertThat(new Day24ImmuneSystemSimulator20XX(fileName).winningArmyUnits()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"51, src/test/resources/day24-demo1.txt",
            "1045, src/test/resources/day24.txt"})
    @Disabled
    void problem2(int expected, String fileName) throws IOException {
        assertThat(new Day24ImmuneSystemSimulator20XX(fileName).immuneSystemUnitsLeft()).isEqualTo(expected);
    }
}