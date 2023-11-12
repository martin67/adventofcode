package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 17: Spinlock")
class Day17SpinlockTest {

    @ParameterizedTest
    @CsvSource({"638, 3",
            "2000, 354"})
    void problem1(int expected, int steps) {
        assertThat(new Day17Spinlock(steps).completedBufferValue()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"10242889, 354"})
    void problem2(int expected, int steps) {
        assertThat(new Day17Spinlock(steps).problem2()).isEqualTo(expected);
    }
}
