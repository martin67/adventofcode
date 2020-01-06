package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3SquaresWithThreeSidesTest {

    @ParameterizedTest
    @CsvSource({"1050, src/test/resources/2016/day3.txt"})
    void validTriangles(long expected, String fileName) throws IOException {
        assertEquals(expected, new Day3SquaresWithThreeSides(fileName).validTriangles());
    }

    @ParameterizedTest
    @CsvSource({"1921, src/test/resources/2016/day3.txt"})
    void validVerticalTriangles(long expected, String fileName) throws IOException {
        assertEquals(expected, new Day3SquaresWithThreeSides(fileName).validVerticalTriangles());
    }

}