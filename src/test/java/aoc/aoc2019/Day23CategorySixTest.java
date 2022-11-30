package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 23: Category Six")
class Day23CategorySixTest {

    @Test
    void problem1() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day23.txt"));
        assertEquals(24954, new Day23CategorySix(inputLines).yValue());
    }

    @Test
    void problem2() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day23.txt"));
        assertEquals(17091, new Day23CategorySix(inputLines).repeatedYValue());
    }

}