package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 12: Passage Pathing")
class Day12PassagePathingTest {

    @ParameterizedTest
    @CsvSource({"10, src/test/resources/2021/day12-demo1.txt",
            "19, src/test/resources/2021/day12-demo2.txt",
            "226, src/test/resources/2021/day12-demo3.txt",
            "3410, src/test/resources/2021/day12.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day12PassagePathing(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"36, src/test/resources/2021/day12-demo1.txt",
            "103, src/test/resources/2021/day12-demo2.txt",
            "3509, src/test/resources/2021/day12-demo3.txt",
            "98796, src/test/resources/2021/day12.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day12PassagePathing(inputLines).problem2());
    }

}