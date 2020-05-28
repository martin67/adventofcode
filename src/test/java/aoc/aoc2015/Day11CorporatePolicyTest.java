package aoc.aoc2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11CorporatePolicyTest {
    @ParameterizedTest
    @CsvSource({"false, hijklmmn",
            "false, abbceffg",
            "false, abbcegjk",
            "true, abcdffaa",
            "true, ghjaabcc"})
    void validPassword(boolean expected, String password) {
        assertEquals(expected, new Day11CorporatePolicy().validPassword(password));
    }

    @ParameterizedTest
    @CsvSource({"abcdffaa, abcdefgh",
            "ghjaabcc, ghijklmn",
            "vzbxxyzz, vzbxkghb",
            "vzcaabcc, vzbxxyzz"})
    void nextPassword(String expected, String password) {
        assertEquals(expected, new Day11CorporatePolicy().nextPassword(password));
    }
}