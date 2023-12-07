package aoc.aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2023: Day 7: Camel Cards")
class Day7CamelCardsTest {

    @ParameterizedTest
    @CsvSource({"6440, day7-demo1.txt",
            "250602641, day7.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day7CamelCards(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"5905, day7-demo1.txt",
            "251037509, day7.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day7CamelCards(inputLines).problem2()).isEqualTo(expected);
    }
}