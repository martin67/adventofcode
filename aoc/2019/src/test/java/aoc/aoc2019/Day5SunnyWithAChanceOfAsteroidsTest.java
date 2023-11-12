package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 5: Sunny with a Chance of Asteroids")
class Day5SunnyWithAChanceOfAsteroidsTest {

    @Test
    void problem1() throws IOException {
        var inputLines = AocFiles.readAllLines("day5.txt");
        for (String opcodes : inputLines) {
            assertThat(new Day5SunnyWithAChanceOfAsteroids().diagnosticCode(opcodes, 1)).isEqualTo(13933662);
        }
    }

    @Test
    void problem2() throws IOException {
        var inputLines = AocFiles.readAllLines("day5.txt");
        for (String opcodes : inputLines) {
            assertThat(new Day5SunnyWithAChanceOfAsteroids().diagnosticCode(opcodes, 5)).isEqualTo(2369720);
        }
    }
}