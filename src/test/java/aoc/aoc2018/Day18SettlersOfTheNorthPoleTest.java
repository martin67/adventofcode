package aoc.aoc2018;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Day18SettlersOfTheNorthPoleTest {

    @ParameterizedTest
    @CsvSource({
            "1147, src/test/resources/2018/day18-demo.txt",
            "588436, src/test/resources/2018/day18.txt"
    })
    void computeResourceValue(int outcome, String fileName) throws IOException {
        Day18SettlersOfTheNorthPole day18SettlersOfTheNorthPole = new Day18SettlersOfTheNorthPole(fileName);
        assertEquals(outcome, day18SettlersOfTheNorthPole.computeResourceValue());
    }

    @Test
    void totalResourceValue() throws IOException {
        String fileName = "src/test/resources/2018/day18.txt";
        Day18SettlersOfTheNorthPole day18SettlersOfTheNorthPole = new Day18SettlersOfTheNorthPole(fileName);
        assertEquals(195290, day18SettlersOfTheNorthPole.totalResourceValue());
    }
}