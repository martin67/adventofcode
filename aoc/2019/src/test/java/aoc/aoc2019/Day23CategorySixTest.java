package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 23: Category Six")
class Day23CategorySixTest {

    @Test
    void problem1() throws IOException, InterruptedException, ExecutionException {
        var inputLines = AocFiles.readAllLines("day23.txt");
        assertThat(new Day23CategorySix(inputLines).yValue()).isEqualTo(24954);
    }

    @Test
    void problem2() throws IOException {
        var inputLines = AocFiles.readAllLines("day23.txt");
        assertThat(new Day23CategorySix(inputLines).repeatedYValue()).isEqualTo(17091);
    }
}