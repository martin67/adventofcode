package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 14: ")
class Day14ExtendedPolymerizationTest {

    @ParameterizedTest
    @CsvSource({"1588, src/test/resources/2021/day14-demo1.txt",
            "2170, src/test/resources/2021/day14.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day14ExtendedPolymerization(inputLines).problem1(10));
    }

    @ParameterizedTest
    @CsvSource({"2188189693529, src/test/resources/2021/day14-demo1.txt",
            "0, src/test/resources/2021/day14.txt"})
    void problem2(String expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day14ExtendedPolymerization(inputLines).problem2());
    }

}