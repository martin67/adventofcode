package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 13: Care Package")
class Day13CarePackageTest {

    @Test
    void problem1() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day13.txt"));
        assertEquals(304, new Day13CarePackage(inputLines).numberOfBlockTiles());
    }

    @Test
    void problem2() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day13.txt"));
        assertEquals(14747, new Day13CarePackage(inputLines).lastScore());
    }

}