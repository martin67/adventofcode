package aoc.aoc2016;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 21: Scrambled Letters and Hash")
class Day21ScrambledLettersAndHashTest {

    @ParameterizedTest
    @CsvSource({"decab, abcde, day21-demo1.txt",
            "dbfgaehc, abcdefgh, day21.txt"})
    void problem1(String expected, String input, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day21ScrambledLettersAndHash(inputLines).scramble(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"abcdefgh, dbfgaehc, day21.txt",
            "aghfcdeb, fbgdceah, day21.txt"})
    void problem2(String expected, String input, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day21ScrambledLettersAndHash(inputLines).crack(input)).isEqualTo(expected);
    }
}