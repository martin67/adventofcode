package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 19: Tractor Beam")
class Day19TractorBeamTest {

    @Test
    void problem1() throws IOException, InterruptedException, ExecutionException {
        var inputLines = AocFiles.readAllLines("day19.txt");
        assertThat(new Day19TractorBeam(inputLines).pointsAffected()).isEqualTo(141);
    }

    @Test
    void problem2() throws IOException, InterruptedException, ExecutionException {
        var inputLines = AocFiles.readAllLines("day19.txt");
        assertThat(new Day19TractorBeam(inputLines).closetSquare()).isEqualTo(15641348);
    }
}