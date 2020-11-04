package aoc.aoc2017;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day6MemoryReallocationTest {

    @ParameterizedTest
    @CsvSource({"5, src/test/resources/2017/day6-demo1.txt",
            "5042, src/test/resources/2017/day6.txt"})
    void countCycles(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day6MemoryReallocation(Files.readAllLines(Paths.get(fileName))).countCycles(false));
    }

    @ParameterizedTest
    @CsvSource({"4, src/test/resources/2017/day6-demo1.txt",
            "1086, src/test/resources/2017/day6.txt"})
    void sizeOfLoop(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day6MemoryReallocation(Files.readAllLines(Paths.get(fileName))).countCycles(true));
    }
}