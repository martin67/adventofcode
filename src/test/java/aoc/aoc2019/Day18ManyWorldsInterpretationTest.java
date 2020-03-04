package aoc.aoc2019;

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
            "81, src/test/resources/2019/day18-demo5.txt",
            "4250, src/test/resources/2019/day18.txt"})
    void shortestPath(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18ManyWorldsInterpretation(inputLines, 1).shortestPath());
    }

    @ParameterizedTest
    @CsvSource({"8, src/test/resources/2019/day18-demo6.txt",
            "24, src/test/resources/2019/day18-demo7.txt",
            "32, src/test/resources/2019/day18-demo8.txt",
            "72, src/test/resources/2019/day18-demo9.txt",
            "0, src/test/resources/2019/day18-2.txt"})
    void shortestMultiplePath(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18ManyWorldsInterpretation(inputLines, 4).shortestMultiplePath());
    }
}