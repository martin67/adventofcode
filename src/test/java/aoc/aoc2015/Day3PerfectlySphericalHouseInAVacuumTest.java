package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2015: Day 3: Perfectly Spherical Houses in a Vacuum")
class Day3PerfectlySphericalHouseInAVacuumTest {
    @ParameterizedTest
    @CsvSource({">, 2",
            "^>v<, 4",
            "^v^v^v^v^v, 2"})
    void atLeastOnePresentDemo(String input, int expected) {
        assertEquals(expected, new Day3PerfectlySphericalHouseInAVacuum().atLeastOnePresent(input));
    }

    @Test
    void atLeastOnePresent() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2015/day3.txt"));
        assertEquals(2592, new Day3PerfectlySphericalHouseInAVacuum().atLeastOnePresent(inputLines.get(0)));
    }

    @ParameterizedTest
    @CsvSource({"^v, 3",
            "^>v<, 3",
            "^v^v^v^v^v, 11"})
    void roboSantaDemo(String input, int expected) {
        assertEquals(expected, new Day3PerfectlySphericalHouseInAVacuum().roboSanta(input));
    }

    @Test
    void roboSanta() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2015/day3.txt"));
        assertEquals(2360, new Day3PerfectlySphericalHouseInAVacuum().roboSanta(inputLines.get(0)));
    }
}