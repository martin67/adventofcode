package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 19: An Elephant Named Joseph")
class Day19AnElephantNamedJosephTest {

    @ParameterizedTest
    @CsvSource({"3, 5",
            "1834471, 3014387"})
    void problem1(int expected, int numberOfElves) {
        assertThat(new Day19AnElephantNamedJoseph(numberOfElves).allPresents()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2, 5",
            "5, 7",
            "7, 8",
            "9, 9",
            "1420064, 3014387"})
    void problem2(int expected, int numberOfElves) {
        assertThat(new Day19AnElephantNamedJoseph(numberOfElves).allPresentsAcross4()).isEqualTo(expected);
    }
}