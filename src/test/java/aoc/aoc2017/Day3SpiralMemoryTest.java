package aoc.aoc2017;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3SpiralMemoryTest {
    @ParameterizedTest
    @CsvSource({"0, 10",
            "3, 12",
            "2, 23",
            "31, 1024"})
    void computeCaptcha(int steps, int dataLocation) {
        assertEquals(steps, new Day3SpiralMemory().stepsRequired(dataLocation));
    }
}