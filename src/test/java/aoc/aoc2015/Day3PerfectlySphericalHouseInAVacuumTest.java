package aoc.aoc2015;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(0, new Day3PerfectlySphericalHouseInAVacuum().atLeastOnePresent(inputLines.get(0)));
    }
}