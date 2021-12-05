package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("2019: Day 25: Cryostasis")
class Day25CryostasisTest {

    @Test
    void problem1() throws IOException, InterruptedException, ExecutionException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day25.txt"));
        assertEquals("password", new Day25Cryostasis(inputLines).getPassword());
    }

}