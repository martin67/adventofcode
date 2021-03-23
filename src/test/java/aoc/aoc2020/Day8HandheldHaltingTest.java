package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 8: Handheld Halting")
class Day8HandheldHaltingTest {

    @ParameterizedTest
    @CsvSource({"5, src/test/resources/2020/day8-demo1.txt",
            "1915, src/test/resources/2020/day8.txt"})
    void accumulatorValue(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day8HandheldHalting(inputLines).accumulatorValue());
    }

    @ParameterizedTest
    @CsvSource({"8, src/test/resources/2020/day8-demo1.txt",
            "944, src/test/resources/2020/day8.txt"})
    void accumulatorValueNoLoop(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day8HandheldHalting(inputLines).accumulatorValueNoLoop());
    }

}