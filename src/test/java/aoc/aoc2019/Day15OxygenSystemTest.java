package aoc.aoc2019;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15OxygenSystemTest {
    @Test
    void fewestNumberofMovementCommands() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day15.txt"));
        assertEquals(424, new Day15OxygenSystem(inputLines).fewestNumberOfMovementCommands(false));
    }

    @Test
    void minutesToFill() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day15.txt"));
        assertEquals(446, new Day15OxygenSystem(inputLines).fewestNumberOfMovementCommands(true));
    }
}