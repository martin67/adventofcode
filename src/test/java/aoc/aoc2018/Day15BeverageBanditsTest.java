package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2018: Day 15: Beverage Bandits")
class Day15BeverageBanditsTest {

    @ParameterizedTest
    @CsvSource({"27730, src/test/resources/2018/day15-demo1.txt",
            "36334, src/test/resources/2018/day15-demo2.txt",
            "39514, src/test/resources/2018/day15-demo3.txt",
            "27755, src/test/resources/2018/day15-demo4.txt",
            "28944, src/test/resources/2018/day15-demo5.txt",
            "18740, src/test/resources/2018/day15-demo6.txt",
            "226688, src/test/resources/2018/day15.txt"})
    void problem1(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day15BeverageBandits day15BeverageBandits = new Day15BeverageBandits();
        assertEquals(expected, day15BeverageBandits.computeCombatOutcome(input));
    }

    @ParameterizedTest
    @CsvSource({"4988, src/test/resources/2018/day15-demo1.txt",
            "3478, src/test/resources/2018/day15-demo4.txt",
            "6474, src/test/resources/2018/day15-demo5.txt",
            "1140, src/test/resources/2018/day15-demo6.txt",
            "31284, src/test/resources/2018/day15-demo7.txt",
            "62958, src/test/resources/2018/day15.txt"})
    void problem2(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day15BeverageBandits day15BeverageBandits = new Day15BeverageBandits();
        assertEquals(expected, day15BeverageBandits.computeCombatOutcomeNoDeadElves(input));
    }

}