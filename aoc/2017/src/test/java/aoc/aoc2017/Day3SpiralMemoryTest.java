package aoc.aoc2017;

import aoc.common.Position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2017: Day 3: Spiral Memory")
class Day3SpiralMemoryTest {

    @ParameterizedTest
    @CsvSource({"0, 1",
            "3, 12",
            "2, 23",
            "31, 1024",
            "475, 277678"})
    void problem1(int steps, int dataLocation) {
        assertEquals(steps, new Day3SpiralMemory().stepsRequired(dataLocation));
    }

    @ParameterizedTest
    @CsvSource({"0, 0, 1",
            "1, 0, 2",
            "1, 1, 3",
            "0, 1, 4",
            "-1, 1, 5",
            "-1, 0, 6",
            "-1, -1, 7",
            "0, -1, 8",
            "1, -1, 9",
            "2, -1, 10"})
    void testPosition(int x, int y, int dataLocation) {
        assertEquals(new Position(x, y), new Day3SpiralMemory().getPosition(dataLocation));
    }

    @ParameterizedTest
    @CsvSource({"122, 100",
            "747, 500",
            "279138, 277678"})
    void problem2(int value, int maxValue) {
        assertEquals(value, new Day3SpiralMemory().stressTest(maxValue));
    }
}