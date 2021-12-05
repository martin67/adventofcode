package aoc.aoc2017;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2017: Day 17: Spinlock")
class Day17SpinlockTest {

    @ParameterizedTest
    @CsvSource({"638, 3",
            "2000, 354"})
    void problem1(int expected, int steps) {
        assertEquals(expected, new Day17Spinlock(steps).completedBufferValue());
    }

    @ParameterizedTest
    @CsvSource({"638, 3",
            "2000, 354"})
    @Disabled
    void problem2(int expected, int steps) {
        assertEquals(expected, new Day17Spinlock(steps).secondCompletedBufferValue());
    }

}
