package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 10: Elves Look, Elves Say")
class Day10ElvesLookElvesSayTest {

    @ParameterizedTest
    @CsvSource({"6, 1, 5",
            "360154, 1113122113, 40",
            "5103798, 1113122113, 50"})
    void codeLength(int expected, String start, int iterations) {
        assertEquals(expected, new Day10ElvesLookElvesSay(start).codeLength(iterations));
    }
}