package aoc2019;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13CarePackageTest {
    @Test
    void numberOfBlockTiles() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day13.txt"));
        assertEquals(304, new Day13CarePackage(inputLines).numberOfBlockTiles());
    }

    @Test
    void lastScore() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day13.txt"));
        assertEquals(0, new Day13CarePackage(inputLines).lastScore());
    }
}