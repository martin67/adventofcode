package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 4: Secure Container")
class Day4SecureContainerTest {

    @ParameterizedTest
    @CsvSource({"true, 111111",
            "false, 223450",
            "false, 123789"})
    void validPassword(boolean expected, int password) {
        assertThat(new Day4SecureContainer().validPassword(password)).isEqualTo(expected);
    }

    @Test
    void problem1() {
        assertThat(new Day4SecureContainer().validPasswordRange(402328, 864247)).isEqualTo(454);
    }

    @ParameterizedTest
    @CsvSource({"true, 112233",
            "false, 123444",
            "true, 111122"})
    void validPasswordNoGroup(boolean expected, int password) {
        assertThat(new Day4SecureContainer().validPasswordNoGroup(password)).isEqualTo(expected);
    }

    @Test
    void problem2() {
        assertThat(new Day4SecureContainer().validPasswordNoGroupRange(402328, 864247)).isEqualTo(288);
    }
}
