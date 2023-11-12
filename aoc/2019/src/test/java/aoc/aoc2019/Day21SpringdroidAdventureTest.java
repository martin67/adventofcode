package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 21: Springdroid Adventure")
class Day21SpringdroidAdventureTest {

    @Test
    void problem1() throws IOException, InterruptedException, ExecutionException {
        var inputLines = AocFiles.readAllLines("day21.txt");
        assertThat(new Day21SpringdroidAdventure(inputLines).hullDamage(false)).isEqualTo("19353074");
    }

    @Test
    void problem2() throws IOException, InterruptedException, ExecutionException {
        var inputLines = AocFiles.readAllLines("day21.txt");
        assertThat(new Day21SpringdroidAdventure(inputLines).hullDamage(true)).isEqualTo("1147582556");
    }
}