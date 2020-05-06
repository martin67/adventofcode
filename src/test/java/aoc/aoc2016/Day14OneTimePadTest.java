package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day14OneTimePadTest {

    @ParameterizedTest
    @CsvSource({"22728, abc",
            "18626, ngcjuoqr"})
    void hashIndex(int expected, String salt) {
        assertEquals(expected, new Day14OneTimePad(salt, false).hashIndex());
    }

    @ParameterizedTest
    @CsvSource({"22551, abc",
            "20092, ngcjuoqr"})
    void stretchedHashIndex(int expected, String salt) {
        assertEquals(expected, new Day14OneTimePad(salt, true).hashIndex());
    }
}