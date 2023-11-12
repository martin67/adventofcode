package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 10: Monitoring Station")
class Day10MonitoringStationTest {

    @ParameterizedTest
    @CsvSource({"8, src/test/resources/day10-demo1.txt",
            "33, src/test/resources/day10-demo2.txt",
            "35, src/test/resources/day10-demo3.txt",
            "41, src/test/resources/day10-demo4.txt",
            "210, src/test/resources/day10-demo5.txt",
            "299, src/test/resources/day10.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day10MonitoringStation(inputLines).bestLocation());
    }

    @ParameterizedTest
    @CsvSource({"802, src/test/resources/day10-demo5.txt",
            "1419, src/test/resources/day10.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day10MonitoringStation(inputLines).vaporize());
    }

}