package aoc2019;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day18ManyWorldsInterpretationTest {
    @ParameterizedTest
    @CsvSource({"8, src/test/resources/2019/day18-demo1.txt",
            "86, src/test/resources/2019/day18-demo2.txt",
            "132, src/test/resources/2019/day18-demo3.txt",
            "136, src/test/resources/2019/day18-demo4.txt",
            "281, src/test/resources/2019/day18-demo5.txt",
            "0, src/test/resources/2019/day18.txt"})
    void shortestPath(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18ManyWorldsInterpretation(inputLines).shortestPath());
    }
}