package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 8: Seven Segment Search")
class Day8SevenSegmentSearchTest {

    @ParameterizedTest
    @CsvSource({"0, src/test/resources/2021/day8-demo1.txt",
            "26, src/test/resources/2021/day8-demo2.txt",
            "548, src/test/resources/2021/day8.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day8SevenSegmentSearch(Files.readAllLines(Paths.get(fileName))).problem1());
    }

    @ParameterizedTest
    @CsvSource({"5353, src/test/resources/2021/day8-demo1.txt",
            "61229, src/test/resources/2021/day8-demo2.txt",
            "1074888, src/test/resources/2021/day8.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day8SevenSegmentSearch(Files.readAllLines(Paths.get(fileName))).problem2());
    }

}