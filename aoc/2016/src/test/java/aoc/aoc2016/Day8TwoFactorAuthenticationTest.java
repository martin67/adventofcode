package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 8: Two-Factor Authentication")
class Day8TwoFactorAuthenticationTest {

    @ParameterizedTest
    @CsvSource({"6, 7, 3, src/test/resources/day8-demo1.txt",
            "106, 50, 6, src/test/resources/day8.txt"})
    void problem1(long expected, int screenWidth, int screenHeight, String fileName) throws IOException {
        assertThat(new Day8TwoFactorAuthentication(screenWidth, screenHeight, fileName).pixelsLit()).isEqualTo(expected);
    }
}