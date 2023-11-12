package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2016: Day 12: Leonardo's Monorail")
class Day12LeonardosMonorailTest {

    @ParameterizedTest
    @CsvSource({"42, src/test/resources/day12-demo1.txt",
            "318020, src/test/resources/day12.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day12LeonardosMonorail(inputLines).registerA());
    }

    @ParameterizedTest
    @CsvSource({"9227674, src/test/resources/day12.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day12LeonardosMonorail(inputLines).registerAwithC());
    }
}