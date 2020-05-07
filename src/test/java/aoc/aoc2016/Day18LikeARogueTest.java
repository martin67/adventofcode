package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18LikeARogueTest {

    @ParameterizedTest
    @CsvSource({"38, 10, src/test/resources/2016/day18-demo1.txt",
            "2035, 40, src/test/resources/2016/day18.txt",
            "20000577, 400000, src/test/resources/2016/day18.txt"})
    void safeTiles(long expected, int rows, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18LikeARogue(inputLines).safeTiles(rows));
    }

}