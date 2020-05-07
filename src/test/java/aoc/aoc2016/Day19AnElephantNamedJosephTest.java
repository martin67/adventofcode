package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19AnElephantNamedJosephTest {

    @ParameterizedTest
    @CsvSource({"3, 5",
            "1834471, 3014387"})
    void allPresents(int expected, int numberOfElves) {
        assertEquals(expected, new Day19AnElephantNamedJoseph(numberOfElves).allPresents());
    }

}