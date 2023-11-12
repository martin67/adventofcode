package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 23: Crab Cups")
class Day23CrabCupsTest {

    @ParameterizedTest
    @CsvSource({"67384529, 389125467",
            "24798635, 362981754"})
    void problem1(int expected, int start) {
        assertThat(new Day23CrabCups(start).problem1(100)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"149245887792, 389125467",
            "12757828710, 362981754"})
    void problem2(long expected, int start) {
        assertThat(new Day23CrabCups(start).problem2(10000000)).isEqualTo(expected);
    }
}