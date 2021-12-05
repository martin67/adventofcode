package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2017: Day 10: Knot Hash")
class Day10KnotHashTest {

    @ParameterizedTest
    @CsvSource({"12, 5, src/test/resources/2017/day10-demo1.txt",
            "11375, 256, src/test/resources/2017/day10.txt"})
    void problem1(int expected, int listSize, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day10KnotHash(listSize).checkKnot(inputLines.get(0)));
    }

    @ParameterizedTest
    @CsvSource({"a2582a3a0e66e6e86e3812dcb672a272, ''",
            "33efeb34ea91902bb2f59c9920caa6cd, 'AoC 2017'",
            "3efbe78a8d82f29979031a4aa0b16a9d, '1,2,3'",
            "63960835bcdc130f0b66d7ff4f6a5a8e, '1,2,4'"})
    void knotHash(String expected, String input) {
        assertEquals(expected, new Day10KnotHash(256).knotHash(input));
    }

    @ParameterizedTest
    @CsvSource({"e0387e2ad112b7c2ef344e44885fe4d8, src/test/resources/2017/day10.txt"})
    void problem2(String expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day10KnotHash(256).knotHash(inputLines.get(0)));
    }

}