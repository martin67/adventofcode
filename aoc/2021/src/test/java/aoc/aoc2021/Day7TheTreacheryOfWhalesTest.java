package aoc.aoc2021;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2021: Day 7: The Treachery of Whales")
class Day7TheTreacheryOfWhalesTest {

    @ParameterizedTest
    @CsvSource({"37, day7-demo1.txt",
            "352254, day7.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day7TheTreacheryOfWhales(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"168, day7-demo1.txt",
            "99053143, day7.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day7TheTreacheryOfWhales(inputLines).problem2()).isEqualTo(expected);
    }
}