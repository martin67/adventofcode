package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 11: Space Police")
class Day11SpacePoliceTest {

    @Test
    void problem1() throws IOException, InterruptedException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/day11.txt"));
        assertEquals(1681, new Day11SpacePolice(inputLines).numberOfPanelsPainted(false));
    }

    @Test
    void problem2() throws IOException, InterruptedException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/day11.txt"));
        assertEquals(250, new Day11SpacePolice(inputLines).numberOfPanelsPainted(true));
    }

}