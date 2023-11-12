package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 1: No Time for a Taxicab")
class Day1NoTimeForATaxicabTest {

    @ParameterizedTest
    @CsvSource({"5, src/test/resources/day1-demo1.txt",
            "2, src/test/resources/day1-demo2.txt",
            "12, src/test/resources/day1-demo3.txt",
            "250, src/test/resources/day1.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertThat(new Day1NoTimeForATaxicab(fileName).shortestPath()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"4, src/test/resources/day1-demo4.txt",
            "151, src/test/resources/day1.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertThat(new Day1NoTimeForATaxicab(fileName).visitedTwice()).isEqualTo(expected);
    }
}