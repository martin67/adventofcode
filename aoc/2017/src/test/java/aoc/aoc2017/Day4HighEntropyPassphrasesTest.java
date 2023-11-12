package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 4: High-Entropy Passphrases")
class Day4HighEntropyPassphrasesTest {
    @ParameterizedTest
    @CsvSource({"true, aa bb cc dd ee",
            "false, aa bb cc dd aa",
            "true, aa bb cc dd aaa"})
    void validPasshrase(boolean valid, String passphrase) {
        assertThat(new Day4HighEntropyPassphrases().validPassphrase(passphrase)).isEqualTo(valid);
    }

    @Test
    void problem1() throws IOException {
        var inputLines = AocFiles.readAllLines("day4.txt");
        assertThat(new Day4HighEntropyPassphrases().numberOfValidPassphrases(inputLines)).isEqualTo(451);
    }

    @ParameterizedTest
    @CsvSource({"true, abcde fghij",
            "false, abcde xyz ecdab",
            "true, a ab abc abd abf abj",
            "true, iiii oiii ooii oooi oooo",
            "false, oiii ioii iioi iiio"})
    void validAnagramPasshrase(boolean valid, String passphrase) {
        assertThat(new Day4HighEntropyPassphrases().validAnagramPassphrase(passphrase)).isEqualTo(valid);
    }

    @Test
    void problem2() throws IOException {
        var inputLines = AocFiles.readAllLines("day4.txt");
        assertThat(new Day4HighEntropyPassphrases().numberOfValidAnagramPassphrases(inputLines)).isEqualTo(223);
    }
}