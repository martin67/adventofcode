package aoc.aoc2015;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1NotQuiteLispTest {
    @ParameterizedTest
    @CsvSource({"(()), 0",
            "()(), 0",
            "(((, 3",
            "(()(()(, 3",
            "))(((((, 3",
            "()), -1",
            "()), -1",
            "))), -3",
            ")())()), -3"})
    void getFloorDemo(String input, int expected) {
        assertEquals(expected, new Day1NotQuiteLisp().getFloor(input));
    }

    @Test
    void getFloor() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2015/day1.txt"));
        assertEquals(232, new Day1NotQuiteLisp().getFloor(inputLines.get(0)));
    }

    @ParameterizedTest
    @CsvSource({"), 1",
            "()()), 5"})
    void getPositionDemo(String input, int expected) {
        assertEquals(expected, new Day1NotQuiteLisp().getPosition(input));
    }

    @Test
    void getPosition() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2015/day1.txt"));
        assertEquals(1783, new Day1NotQuiteLisp().getPosition(inputLines.get(0)));
    }
}