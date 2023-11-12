package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 3: Squares With Three Sides")
class Day3SquaresWithThreeSidesTest {

    @ParameterizedTest
    @CsvSource({"1050, src/test/resources/day3.txt"})
    void problem1(long expected, String fileName) throws IOException {
        assertThat(new Day3SquaresWithThreeSides(fileName).validTriangles()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1921, src/test/resources/day3.txt"})
    void problem2(long expected, String fileName) throws IOException {
        assertThat(new Day3SquaresWithThreeSides(fileName).validVerticalTriangles()).isEqualTo(expected);
    }
}