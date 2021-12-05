package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2016: Day 3: Squares With Three Sides")
class Day3SquaresWithThreeSidesTest {

    @ParameterizedTest
    @CsvSource({"1050, src/test/resources/2016/day3.txt"})
    void problem1(long expected, String fileName) throws IOException {
        assertEquals(expected, new Day3SquaresWithThreeSides(fileName).validTriangles());
    }

    @ParameterizedTest
    @CsvSource({"1921, src/test/resources/2016/day3.txt"})
    void problem2(long expected, String fileName) throws IOException {
        assertEquals(expected, new Day3SquaresWithThreeSides(fileName).validVerticalTriangles());
    }

}