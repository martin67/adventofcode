package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 17: ")
class Day17Test {

    @ParameterizedTest
    @CsvSource({"0, src/test/resources/2021/day17-demo1.txt",
            "0, src/test/resources/2021/day17.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day17(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"0, src/test/resources/2021/day17-demo1.txt",
            "0, src/test/resources/2021/day17.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day17(inputLines).problem2());
    }

}