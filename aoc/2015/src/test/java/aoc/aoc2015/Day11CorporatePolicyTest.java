package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 11: Corporate Policy")
class Day11CorporatePolicyTest {
    @ParameterizedTest
    @CsvSource({"false, hijklmmn",
            "false, abbceffg",
            "false, abbcegjk",
            "true, abcdffaa",
            "true, ghjaabcc"})
    void problem1(boolean expected, String password) {
        assertThat(new Day11CorporatePolicy().validPassword(password)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"abcdffaa, abcdefgh",
            "ghjaabcc, ghijklmn",
            "vzbxxyzz, vzbxkghb",
            "vzcaabcc, vzbxxyzz"})
    void problem2(String expected, String password) {
        assertThat(new Day11CorporatePolicy().nextPassword(password)).isEqualTo(expected);
    }
}