package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 20: Trench Map")
class Day20TrenchMapTest {

    @ParameterizedTest
    @CsvSource({"35, src/test/resources/2021/day20-demo1.txt",
            "5097, src/test/resources/2021/day20.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day20TrenchMap(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"3351, src/test/resources/2021/day20-demo1.txt",
            "0, src/test/resources/2021/day20.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day20TrenchMap(inputLines).problem2());
    }
//18251 too high
}