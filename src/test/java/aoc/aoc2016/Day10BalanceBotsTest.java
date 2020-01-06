package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10BalanceBotsTest {

    @ParameterizedTest
    @CsvSource({"2, 2, 5, src/test/resources/2016/day10-demo1.txt",
            "93, 61, 17, src/test/resources/2016/day10.txt"})
    void botNumber(int expected, int compareOne, int compareTwo, String fileName) throws IOException {
        assertEquals(expected, new Day10BalanceBots(fileName).botNumber(compareOne, compareTwo));
    }

    @ParameterizedTest
    @CsvSource({"47101, src/test/resources/2016/day10.txt"})
    void multiplyOutput(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day10BalanceBots(fileName).multiplyOutput());
    }
}