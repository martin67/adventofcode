package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 4: Giant Squid")
class Day4GiantSquidTest {

    @ParameterizedTest
    @CsvSource({"4512, src/test/resources/2021/day4-demo1.txt",
            "8136, src/test/resources/2021/day4.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day4GiantSquid(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"1924, src/test/resources/2021/day4-demo1.txt",
            "12738, src/test/resources/2021/day4.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day4GiantSquid(inputLines).problem2());
    }
}