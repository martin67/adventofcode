package aoc.aoc2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12RainRiskTest {

    @ParameterizedTest
    @CsvSource({"25, src/test/resources/2020/day12-demo1.txt",
            "636, src/test/resources/2020/day12.txt"})
    void distance(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day12RainRisk(inputLines).distance());
    }

    @ParameterizedTest
    @CsvSource({"286, src/test/resources/2020/day12-demo1.txt",
            "26841, src/test/resources/2020/day12.txt"})
    void distanceWithWaypoint(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day12RainRisk(inputLines).distanceWithWaypoint());
    }

}
