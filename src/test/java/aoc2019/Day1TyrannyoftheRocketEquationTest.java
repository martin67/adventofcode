package aoc2019;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1TyrannyoftheRocketEquationTest {

    @ParameterizedTest
    @CsvSource({"2, 12",
            "2, 14",
            "654, 1969",
            "33583, 100756"})
    void fuelRequirement(int checksum, int mass) {
        assertEquals(checksum, new Day1TyrannyoftheRocketEquation().fuelRequirement(mass));
    }

    @Test
    void fuelRequirementFromFile() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day1.txt"));
        assertEquals(3478233, new Day1TyrannyoftheRocketEquation().sumOfFuelRequirements(inputLines));
    }

    @ParameterizedTest
    @CsvSource({"2, 14",
            "966, 1969",
            "50346, 100756"})
    void fuelRequirementAddedFuel(int checksum, int mass) {
        assertEquals(checksum, new Day1TyrannyoftheRocketEquation().fuelRequirementAddedFuel(mass));
    }

    @Test
    void fuelRequirementAddedFuelFromFile() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day1.txt"));
        assertEquals(5214475, new Day1TyrannyoftheRocketEquation().sumOfFuelRequirementsAddedFuelFromFile(inputLines));
    }
}