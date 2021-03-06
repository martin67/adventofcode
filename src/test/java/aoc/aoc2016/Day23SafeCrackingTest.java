package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23SafeCrackingTest {

    @ParameterizedTest
    @CsvSource({"3, src/test/resources/2016/day23-demo1.txt",
            "12703, src/test/resources/2016/day23.txt"})
    void safeValue(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day23SafeCracking(inputLines).safeValue());
    }

    @ParameterizedTest
    @CsvSource({"479009263, src/test/resources/2016/day23.txt"})
    void secondValue(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day23SafeCracking(inputLines).secondValue());
    }

}