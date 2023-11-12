package aoc.aoc2015;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 3: Perfectly Spherical Houses in a Vacuum")
class Day3PerfectlySphericalHouseInAVacuumTest {
    @ParameterizedTest
    @CsvSource({">, 2",
            "^>v<, 4",
            "^v^v^v^v^v, 2"})
    void atLeastOnePresentDemo(String input, int expected) {
        assertThat(new Day3PerfectlySphericalHouseInAVacuum().atLeastOnePresent(input)).isEqualTo(expected);
    }

    @Test
    void atLeastOnePresent() throws IOException {
        var inputLines = AocFiles.readAllLines("day3.txt");
        assertThat(new Day3PerfectlySphericalHouseInAVacuum().atLeastOnePresent(inputLines.get(0)))
                .isEqualTo(2592);
    }

    @ParameterizedTest
    @CsvSource({"^v, 3",
            "^>v<, 3",
            "^v^v^v^v^v, 11"})
    void roboSantaDemo(String input, int expected) {
        assertThat(new Day3PerfectlySphericalHouseInAVacuum().roboSanta(input)).isEqualTo(expected);
    }

    @Test
    void roboSanta() throws IOException {
        var inputLines = AocFiles.readAllLines("day3.txt");
        assertThat(new Day3PerfectlySphericalHouseInAVacuum().roboSanta(inputLines.get(0))).isEqualTo(2360);
    }
}