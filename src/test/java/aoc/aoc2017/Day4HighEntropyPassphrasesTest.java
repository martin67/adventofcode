package aoc.aoc2017;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4HighEntropyPassphrasesTest {
    @ParameterizedTest
    @CsvSource({"true, aa bb cc dd ee",
            "false, aa bb cc dd aa",
            "true, aa bb cc dd aaa"})
    void validPasshrase(boolean valid, String passphrase) {
        assertEquals(valid, new Day4HighEntropyPassphrases().validPassphrase(passphrase));
    }

    @Test
    void numberOfValidPassphrases() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2017/day4.txt"));
        for (String line : inputLines) {
            assertEquals(995, new Day1InverseCaptcha().computeCaptcha(line));
        }
    }
}