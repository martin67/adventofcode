package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 5: Hydrothermal Venture")
class Day5HydrothermalVentureTest {

    @ParameterizedTest
    @CsvSource({"5, src/test/resources/2021/day5-demo1.txt",
            "5608, src/test/resources/2021/day5.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day5HydrothermalVenture(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"12, src/test/resources/2021/day5-demo1.txt",
            "20299, src/test/resources/2021/day5.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day5HydrothermalVenture(inputLines).problem2());
    }

}