package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2017: Day 15: Dueling Generators")
class Day15DuelingGeneratorsTest {

    @ParameterizedTest
    @CsvSource({"588, 65, 8921",
            "577, 618, 814"})
    void problem1(int expected, int startA, int startB) {
        assertEquals(expected, new Day15DuelingGenerators(startA, startB).finalCount());
    }

    @ParameterizedTest
    @CsvSource({"309, 65, 8921",
            "316, 618, 814"})
    void problem2(int expected, int startA, int startB) {
        assertEquals(expected, new Day15DuelingGenerators(startA, startB).finalMultipleCount());
    }

}