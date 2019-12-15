package aoc2019;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15OxygenSystemTest {
    @Test
    void fewestNumberofMovementCommands() throws IOException, InterruptedException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day15.txt"));
        assertEquals(0, new Day15OxygenSystem(inputLines).fewestNumberOfMovementCommands());
    }
}