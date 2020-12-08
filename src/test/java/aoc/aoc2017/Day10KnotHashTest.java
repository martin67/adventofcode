package aoc.aoc2017;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10KnotHashTest {

    @ParameterizedTest
    @CsvSource({"12, 5, src/test/resources/2017/day10-demo1.txt",
            "11375, 256, src/test/resources/2017/day10.txt"})
    void checkKnot(int expected, int listSize, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day10KnotHash(listSize, inputLines).checkKnot());
    }

    @ParameterizedTest
    @CsvSource({"e0387e2ad112b7c2ef344e44885fe4d8, 256, src/test/resources/2017/day10.txt"})
    void knotHash(String expected, int listSize, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day10KnotHash(listSize, inputLines).knotHash());
    }

}