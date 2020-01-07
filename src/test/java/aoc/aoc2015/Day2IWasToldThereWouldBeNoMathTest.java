package aoc.aoc2015;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2IWasToldThereWouldBeNoMathTest {
    @ParameterizedTest
    @CsvSource({"2x3x4, 58",
            "1x1x10, 43"})
    void getSquareFeetDemo(String input, int expected) throws Exception {
        assertEquals(expected, new Day2IWasToldThereWouldBeNoMath().getSquareFeet(Collections.singletonList(input)));
    }

    @Test
    void getSquareFeet() throws Exception {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2015/day2.txt"));
        assertEquals(1588178, new Day2IWasToldThereWouldBeNoMath().getSquareFeet(inputLines));
    }

    @ParameterizedTest
    @CsvSource({"2x3x4, 34",
            "1x1x10, 14"})
    void getRibbonDemo(String input, int expected) {
        assertEquals(expected, new Day2IWasToldThereWouldBeNoMath().getRibbon(Collections.singletonList(input)));
    }

    @Test
    void getRibbon() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2015/day2.txt"));
        assertEquals(1588178, new Day2IWasToldThereWouldBeNoMath().getRibbon(inputLines));
    }
}