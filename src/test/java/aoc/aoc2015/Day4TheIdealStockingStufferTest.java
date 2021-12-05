package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2015: Day 4: The Ideal Stocking Stuffer")
class Day4TheIdealStockingStufferTest {
    @ParameterizedTest
    @CsvSource({"abcdef, 609043",
            "pqrstuv, 1048970",
            "iwrupvqb, 346386"})
    void lowestNumber(String secretKey, int expected) throws NoSuchAlgorithmException {
        assertEquals(expected, new Day4TheIdealStockingStuffer().lowestNumber(secretKey, 5));
    }

    @Test
    void lowestNumber6() throws NoSuchAlgorithmException {
        assertEquals(9958218, new Day4TheIdealStockingStuffer().lowestNumber("iwrupvqb", 6));
    }
}