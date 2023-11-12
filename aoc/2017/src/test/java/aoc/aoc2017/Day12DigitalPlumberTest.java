package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2017: Day 12: Digital Plumber")
class Day12DigitalPlumberTest {

    @ParameterizedTest
    @CsvSource({"6, src/test/resources/day12-demo1.txt",
            "175, src/test/resources/day12.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day12DigitalPlumber(Files.readAllLines(Paths.get(fileName))).programsInGroup());
    }

    @ParameterizedTest
    @CsvSource({"2, src/test/resources/day12-demo1.txt",
            "213, src/test/resources/day12.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day12DigitalPlumber(Files.readAllLines(Paths.get(fileName))).numberOfGroups());
    }

}