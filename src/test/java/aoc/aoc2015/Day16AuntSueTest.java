package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 16: Aunt Sue")
class Day16AuntSueTest {

    @ParameterizedTest
    @CsvSource({"213, src/test/resources/2015/day16.txt"})
    void sueNumber(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day16AuntSue(inputLines).sueNumber());
    }

    @ParameterizedTest
    @CsvSource({"323, src/test/resources/2015/day16.txt"})
    void realSueNumber(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day16AuntSue(inputLines).realSueNumber());
    }
}