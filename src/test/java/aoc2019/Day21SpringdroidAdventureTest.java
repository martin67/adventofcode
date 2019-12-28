package aoc2019;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21SpringdroidAdventureTest {
    @Test
    void getPassword() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day21.txt"));
        assertEquals(0, new Day21SpringdroidAdventure(inputLines).hullDamage());
    }
}