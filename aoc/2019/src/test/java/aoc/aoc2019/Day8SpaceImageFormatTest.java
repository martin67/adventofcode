package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 8: Space Image Format")
class Day8SpaceImageFormatTest {

    @Test
    void problem1() throws Exception {
        var inputLines = AocFiles.readAllLines("day8.txt");
        assertThat(new Day8SpaceImageFormat(inputLines).digitChecksum()).isEqualTo(2480);
    }
}