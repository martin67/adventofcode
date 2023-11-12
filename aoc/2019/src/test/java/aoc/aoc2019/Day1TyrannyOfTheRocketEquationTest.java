package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 1: The Tyranny of the Rocket Equation")
class Day1TyrannyOfTheRocketEquationTest {

    @ParameterizedTest
    @CsvSource({"2, 12",
            "2, 14",
            "654, 1969",
            "33583, 100756"})
    void fuelRequirement(int checksum, int mass) {
        assertThat(new Day1TyrannyOfTheRocketEquation().fuelRequirement(mass)).isEqualTo(checksum);
    }

    @Test
    void problem1() throws IOException {
        var inputLines = AocFiles.readAllLines("day1.txt");
        assertThat(new Day1TyrannyOfTheRocketEquation().sumOfFuelRequirements(inputLines)).isEqualTo(3478233);
    }

    @ParameterizedTest
    @CsvSource({"2, 14",
            "966, 1969",
            "50346, 100756"})
    void fuelRequirementAddedFuel(int checksum, int mass) {
        assertThat(new Day1TyrannyOfTheRocketEquation().fuelRequirementAddedFuel(mass)).isEqualTo(checksum);
    }

    @Test
    void problem2() throws IOException {
        var inputLines = AocFiles.readAllLines("day1.txt");
        assertThat(new Day1TyrannyOfTheRocketEquation().sumOfFuelRequirementsAddedFuelFromFile(inputLines)).isEqualTo(5214475);
    }
}