package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 11: Dumbo Octopus")
class Day11DumboOctopusTest {

    @ParameterizedTest
    @CsvSource({"1656, src/test/resources/day11-demo1.txt",
            "1625, src/test/resources/day11.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day11DumboOctopus(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"195, src/test/resources/day11-demo1.txt",
            "244, src/test/resources/day11.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day11DumboOctopus(inputLines).problem2());
    }

}