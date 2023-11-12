package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 15: Chiton")
class Day15ChitonTest {

    @ParameterizedTest
    @CsvSource({"40, src/test/resources/day15-demo1.txt",
            "553, src/test/resources/day15.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day15Chiton(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"315, src/test/resources/day15-demo1.txt",
            "2858, src/test/resources/day15.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day15Chiton(inputLines).problem2());
    }

}