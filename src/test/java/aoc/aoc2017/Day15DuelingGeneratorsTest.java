package aoc.aoc2017;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15DuelingGeneratorsTest {

    @ParameterizedTest
    @CsvSource({"588, 65, 8921",
            "577, 618, 814"})
    void finalCount(int expected, int startA, int startB) {
        assertEquals(expected, new Day15DuelingGenerators(startA, startB).finalCount());
    }

    @ParameterizedTest
    @CsvSource({"309, 65, 8921",
            "316, 618, 814"})
    void finalMultipleCount(int expected, int startA, int startB) {
        assertEquals(expected, new Day15DuelingGenerators(startA, startB).finalMultipleCount());
    }

}