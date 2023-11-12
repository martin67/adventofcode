package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2016: Day 10: Balance Bots")
class Day10BalanceBotsTest {

    @ParameterizedTest
    @CsvSource({"2, 2, 5, src/test/resources/day10-demo1.txt",
            "93, 61, 17, src/test/resources/day10.txt"})
    void problem1(int expected, int compareOne, int compareTwo, String fileName) throws IOException {
        assertEquals(expected, new Day10BalanceBots(fileName).botNumber(compareOne, compareTwo));
    }

    @ParameterizedTest
    @CsvSource({"47101, src/test/resources/day10.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day10BalanceBots(fileName).multiplyOutput());
    }
}