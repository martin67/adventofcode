package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 10: Adapter Array")
class Day10AdapterArrayTest {

    @ParameterizedTest
    @CsvSource({"35, src/test/resources/2020/day10-demo1.txt",
            "220, src/test/resources/2020/day10-demo2.txt",
            "2210, src/test/resources/2020/day10.txt"})
    void joltDifference(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day10AdapterArray(inputLines).joltDifference());
    }

    @ParameterizedTest
    @CsvSource({"8, src/test/resources/2020/day10-demo1.txt",
            "19208, src/test/resources/2020/day10-demo2.txt",
            "7086739046912, src/test/resources/2020/day10.txt"})
    void adapterCombinations(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day10AdapterArray(inputLines).adapterCombinations2());
    }

}
