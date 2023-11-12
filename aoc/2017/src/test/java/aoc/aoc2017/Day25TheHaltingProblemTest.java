package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 25: The Halting Problem")
class Day25TheHaltingProblemTest {

    @Test
    void problem1demo() {
        assertThat(new Day25TheHaltingProblem().problem1demo()).isEqualTo(3);
    }

    @Test
    void problem1() {
        assertThat(new Day25TheHaltingProblem().problem1()).isEqualTo(4385);
    }
}