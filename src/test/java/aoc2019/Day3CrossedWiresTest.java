package aoc2019;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3CrossedWiresTest {

    @ParameterizedTest
    @CsvSource({"6, src/test/resources/2019/day3-demo1.txt",
            "159, src/test/resources/2019/day3-demo2.txt",
            "135, src/test/resources/2019/day3-demo3.txt",
            "865, src/test/resources/2019/day3.txt"})
    void distanceToClosestIntersection(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day3CrossedWires(inputLines).distanceToClosestIntersection());
    }

    @ParameterizedTest
    @CsvSource({"30, src/test/resources/2019/day3-demo1.txt",
            "610, src/test/resources/2019/day3-demo2.txt",
            "410, src/test/resources/2019/day3-demo3.txt",
            "35038, src/test/resources/2019/day3.txt"})
    void stepsToClosestIntersection(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day3CrossedWires(inputLines).stepsToClosestIntersection());
    }
}