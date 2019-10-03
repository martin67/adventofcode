package adventofcode2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1NoTimeForATaxicabTest {

    @ParameterizedTest
    @CsvSource({"5, out/test/resources/2016/day1-demo1.txt",
            "2, out/test/resources/2016/day1-demo2.txt",
            "12, out/test/resources/2016/day1-demo3.txt",
            "250, out/test/resources/2016/day1.txt"})
    void shortestPath(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day1NoTimeForATaxicab(fileName).shortestPath());
    }

    @ParameterizedTest
    @CsvSource({"4, out/test/resources/2016/day1-demo4.txt",
            "0, out/test/resources/2016/day1.txt"})
    void visitedTwice(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day1NoTimeForATaxicab(fileName).visitedTwice());
    }
}