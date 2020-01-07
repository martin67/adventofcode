package aoc.aoc2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4TheIdealStockingStufferTest {
    @ParameterizedTest
    @CsvSource({"abcdef, 609043",
            "pqrstuv, 1048970",
            "iwrupvqb, 2"})
    void lowestNumber(String secretKey, int expected) throws NoSuchAlgorithmException {
        assertEquals(expected, new Day4TheIdealStockingStuffer().lowestNumber(secretKey));
    }
}