package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2018: Day 7: The Sum of Its Parts")
class Day7TheSumofItsPartsTest {

    @ParameterizedTest
    @CsvSource({"CABDFE, src/test/resources/2018/day7-demo1.txt",
            "ABGKCMVWYDEHFOPQUILSTNZRJX, src/test/resources/2018/day7.txt"})
    void problem1(String expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day7TheSumofItsParts day7TheSumofItsParts = new Day7TheSumofItsParts();
        assertEquals(expected, day7TheSumofItsParts.puzzleOrder(input));
    }

    @ParameterizedTest
    @CsvSource({"15, 2, 0, src/test/resources/2018/day7-demo1.txt",
            "898, 5, 60, src/test/resources/2018/day7.txt"})
    void problem2(int expected, int numberOfWorkers, int duration, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day7TheSumofItsParts day7TheSumofItsParts = new Day7TheSumofItsParts();
        assertEquals(expected, day7TheSumofItsParts.computeTime(input, numberOfWorkers, duration));
    }

}