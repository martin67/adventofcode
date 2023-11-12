package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 21: RPG Simulator 20XX")
class Day21RpgSimulator20xxTest {

    @Test
    void problem1() {
        Day21RpgSimulator20xx day21RpgSimulator20xx = new Day21RpgSimulator20xx();
        assertThat(day21RpgSimulator20xx.problem1())
                .isEqualTo(111);
    }

    @Test
    void problem2() {
        Day21RpgSimulator20xx day21RpgSimulator20xx = new Day21RpgSimulator20xx();
        assertThat(day21RpgSimulator20xx.problem2())
                .isEqualTo(188);
    }
}