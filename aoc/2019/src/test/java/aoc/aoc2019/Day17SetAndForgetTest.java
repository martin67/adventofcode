package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 17: Set and Forget")
class Day17SetAndForgetTest {

    @Test
    void problem1() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/day17.txt"));
        assertEquals(13580, new Day17SetAndForget(inputLines).sumOfAlignmentParameters());
    }

    @Test
    void problem2() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/day17.txt"));
        assertEquals(1063081, new Day17SetAndForget(inputLines).dustCollected());
    }
}