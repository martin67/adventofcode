package aoc2019;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4SecureContainerTest {

    @ParameterizedTest
    @CsvSource({"true, 111111",
            "false, 223450",
            "false, 123789"})
    void validPassword(boolean expected, int password) {
        assertEquals(expected, new Day4SecureContainer().validPassword(password));
    }

    @Test
    void validPasswordRange() {
        assertEquals(454, new Day4SecureContainer().validPasswordRange(402328, 864247));
    }

    @ParameterizedTest
    @CsvSource({"true, 112233",
            "false, 123444",
            "true, 111122"})
    void validPasswordNoGroup(boolean expected, int password) {
        assertEquals(expected, new Day4SecureContainer().validPasswordNoGroup(password));
    }

    @Test
    void validPasswordNoGroupRange() {
        assertEquals(288, new Day4SecureContainer().validPasswordNoGroupRange(402328, 864247));
    }
}
