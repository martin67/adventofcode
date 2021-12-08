package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 6: Lanternfish")
class Day6LanternfishTest {

    @ParameterizedTest
    @CsvSource({"5934, src/test/resources/2021/day6-demo1.txt",
            "387413, src/test/resources/2021/day6.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day6Lanternfish(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"26984457539, src/test/resources/2021/day6-demo1.txt",
            "1738377086345, src/test/resources/2021/day6.txt"})
    void problem2(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day6Lanternfish(inputLines).problem2());
    }

}