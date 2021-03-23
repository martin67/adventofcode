package aoc.aoc2017;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17SpinlockTest {

    @ParameterizedTest
    @CsvSource({"638, 3",
            "2000, 354"})
    void completedBufferValue(int expected, int steps) {
        assertEquals(expected, new Day17Spinlock(steps).completedBufferValue());
    }

    @ParameterizedTest
    @CsvSource({"638, 3",
            "2000, 354"})
    void secondCompletedBufferValue(int expected, int steps) {
        assertEquals(expected, new Day17Spinlock(steps).secondCompletedBufferValue());
    }

}
