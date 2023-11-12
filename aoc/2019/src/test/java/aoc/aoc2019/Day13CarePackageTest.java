package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 13: Care Package")
class Day13CarePackageTest {

    @Test
    void problem1() throws IOException, InterruptedException, ExecutionException {
        var inputLines = AocFiles.readAllLines("day13.txt");
        assertThat(new Day13CarePackage(inputLines).numberOfBlockTiles()).isEqualTo(304);
    }

    @Test
    void problem2() throws IOException, InterruptedException, ExecutionException {
        var inputLines = AocFiles.readAllLines("day13.txt");
        assertThat(new Day13CarePackage(inputLines).lastScore()).isEqualTo(14747);
    }
}