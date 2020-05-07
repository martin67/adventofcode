package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15TimingIsEverythingTest {
    @ParameterizedTest
    @CsvSource({"5, src/test/resources/2016/day15-demo1.txt",
            "16824, src/test/resources/2016/day15.txt",
            "3543984, src/test/resources/2016/day15-2.txt"})
    void firstTime(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day15TimingIsEverything(inputLines).firstTime());
    }

}