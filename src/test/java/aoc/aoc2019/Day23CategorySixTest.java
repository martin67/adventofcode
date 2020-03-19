package aoc.aoc2019;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23CategorySixTest {
    @Test
    void yValue() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day23.txt"));
        assertEquals(24954, new Day23CategorySix(inputLines).yValue());
    }

    @Test
    void repeatedYValue() throws IOException, ExecutionException, InterruptedException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day23.txt"));
        assertEquals(17091, new Day23CategorySix(inputLines).repeatedYValue());
    }
}