package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 1: Chronal Calibration")
class Day1ChronalCalibrationTest {

    @ParameterizedTest
    @CsvSource({
            "0 1 -2 3 1, 3",
            "1 1 1, 3",
            "1 1 -2, 0",
            "-1 -2 -3, -6"
    })
    void computeFrequency(String input, Integer expected) {
        Day1ChronalCalibration day1ChronalCalibration = new Day1ChronalCalibration();
        assertThat(day1ChronalCalibration.computeFrequency(input)).isEqualTo(expected);
    }

    @Test
    void problem1() throws IOException {
        Day1ChronalCalibration day1ChronalCalibration = new Day1ChronalCalibration();
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/day1.txt"))));
        assertThat(day1ChronalCalibration.computeFrequency(input)).isEqualTo(582);
    }

    @ParameterizedTest
    @CsvSource({
            "1 -2 3 1 1 -2, 2",
            "1 -1, 0",
            "3 3 4 -2 -4, 10",
            "-6 3 8 5 -6, 5",
            "7 7 -2 -7 -4, 14"
    })
    void computeDoubleFrequency(String input, Integer expected) {
        Day1ChronalCalibration day1ChronalCalibration = new Day1ChronalCalibration();
        assertThat(day1ChronalCalibration.computeDoubleFrequency(input)).isEqualTo(expected);
    }

    @Test
    void problem2() throws IOException {
        Day1ChronalCalibration day1ChronalCalibration = new Day1ChronalCalibration();
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/day1.txt"))));
        assertThat(day1ChronalCalibration.computeDoubleFrequency(input)).isEqualTo(488);
    }
}