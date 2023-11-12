package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 4: The Ideal Stocking Stuffer")
class Day4TheIdealStockingStufferTest {
    @ParameterizedTest
    @CsvSource({"abcdef, 609043",
            "pqrstuv, 1048970",
            "iwrupvqb, 346386"})
    void problem1(String secretKey, int expected) {
        assertThat(new Day4TheIdealStockingStuffer().lowestNumber(secretKey, 5))
                .isEqualTo(expected);
    }

    @Test
    void problem2() {
        assertThat(new Day4TheIdealStockingStuffer().lowestNumber("iwrupvqb", 6))
                .isEqualTo(9958218);
    }
}