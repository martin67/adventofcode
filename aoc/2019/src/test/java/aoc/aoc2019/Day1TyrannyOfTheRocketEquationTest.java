package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 1: The Tyranny of the Rocket Equation")
class Day1TyrannyOfTheRocketEquationTest {

    @ParameterizedTest
    @CsvSource({"2, 12",
            "2, 14",
            "654, 1969",
            "33583, 100756"})
    void fuelRequirement(int checksum, int mass) {
        assertEquals(checksum, new Day1TyrannyOfTheRocketEquation().fuelRequirement(mass));
    }

    @Test
    void problem1() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/day1.txt"));
        assertEquals(3478233, new Day1TyrannyOfTheRocketEquation().sumOfFuelRequirements(inputLines));
    }

    @ParameterizedTest
    @CsvSource({"2, 14",
            "966, 1969",
            "50346, 100756"})
    void fuelRequirementAddedFuel(int checksum, int mass) {
        assertEquals(checksum, new Day1TyrannyOfTheRocketEquation().fuelRequirementAddedFuel(mass));
    }

    @Test
    void problem2() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/day1.txt"));
        assertEquals(5214475, new Day1TyrannyOfTheRocketEquation().sumOfFuelRequirementsAddedFuelFromFile(inputLines));
    }

}