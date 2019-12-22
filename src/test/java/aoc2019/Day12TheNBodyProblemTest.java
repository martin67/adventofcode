package aoc2019;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12TheNBodyProblemTest {

    @ParameterizedTest
    @CsvSource({"179, 10, src/test/resources/2019/day12-demo1.txt",
            "1940, 100, src/test/resources/2019/day12-demo2.txt",
            "6490, 1000, src/test/resources/2019/day12.txt"})
    void totalEnergy(int expected, int steps, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day12TheNBodyProblem(inputLines).totalEnergy(steps));
    }

    @ParameterizedTest
    @CsvSource({"2772, src/test/resources/2019/day12-demo1.txt",
            "0, src/test/resources/2019/day12.txt"})
    void stepsToOriginalState(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        //assertEquals(expected, new Day12TheNBodyProblem(inputLines).stepsToOriginalState());
    }

}