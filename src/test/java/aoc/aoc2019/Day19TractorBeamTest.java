package aoc.aoc2019;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class Day19TractorBeamTest {

    @Test
    void pointsAffected() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day19.txt"));
        assertEquals(141, new Day19TractorBeam(inputLines).pointsAffected());
    }

    @Test
    void closetSquare() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day19.txt"));
        assertEquals(15791361, new Day19TractorBeam(inputLines).closetSquare());
    }
}