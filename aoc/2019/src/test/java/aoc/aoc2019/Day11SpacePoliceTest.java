package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 11: Space Police")
class Day11SpacePoliceTest {

    @Test
    void problem1() throws IOException, InterruptedException {
        var inputLines = AocFiles.readAllLines("day11.txt");
        assertThat(new Day11SpacePolice(inputLines).numberOfPanelsPainted(false)).isEqualTo(1681);
    }

    @Test
    void problem2() throws IOException, InterruptedException {
        var inputLines = AocFiles.readAllLines("day11.txt");
        assertThat(new Day11SpacePolice(inputLines).numberOfPanelsPainted(true)).isEqualTo(250);
    }
}