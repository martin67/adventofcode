package aoc2018;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23ExperimentalEmergencyTeleportationTest {

    @ParameterizedTest
    @CsvSource({"7, src/test/resources/2018/day23-demo0.txt",
            "935, src/test/resources/2018/day23.txt"})
    void nanoBotsInRange(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day23ExperimentalEmergencyTeleportation(fileName).nanobotsInRange());
    }

    @ParameterizedTest
    @CsvSource({"36, src/test/resources/2018/day23-demo1.txt",
            "138697281, src/test/resources/2018/day23.txt"})
    void shortestDistance(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day23ExperimentalEmergencyTeleportation(fileName).shortestDistance());
    }

}