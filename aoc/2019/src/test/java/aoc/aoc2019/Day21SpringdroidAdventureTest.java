package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 21: Springdroid Adventure")
class Day21SpringdroidAdventureTest {

    @Test
    void problem1() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/day21.txt"));
        assertEquals("19353074", new Day21SpringdroidAdventure(inputLines).hullDamage(false));
    }

    @Test
    void problem2() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/day21.txt"));
        assertEquals("1147582556", new Day21SpringdroidAdventure(inputLines).hullDamage(true));
    }

}