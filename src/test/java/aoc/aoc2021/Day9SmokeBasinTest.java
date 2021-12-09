package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 9: Smoke Basin")
class Day9SmokeBasinTest {

    @ParameterizedTest
    @CsvSource({"15, src/test/resources/2021/day9-demo1.txt",
            "535, src/test/resources/2021/day9.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day9SmokeBasin(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"1134, src/test/resources/2021/day9-demo1.txt",
            "1122700, src/test/resources/2021/day9.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day9SmokeBasin(inputLines).problem2());
    }

}