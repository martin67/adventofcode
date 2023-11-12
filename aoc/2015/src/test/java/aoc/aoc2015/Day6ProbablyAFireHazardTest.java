package aoc.aoc2015;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 6: Probably a Fire Hazard")
class Day6ProbablyAFireHazardTest {
    @Test
    void problem1() throws IOException {
        var inputLines = AocFiles.readAllLines("day6.txt");
        assertThat(new Day6ProbablyAFireHazard().lightsLit(inputLines, false)).isEqualTo(543903);
    }

    @Test
    void problem2() throws IOException {
        var inputLines = AocFiles.readAllLines("day6.txt");
        assertThat(new Day6ProbablyAFireHazard().lightsLit(inputLines, true)).isEqualTo(14687245);
    }
}