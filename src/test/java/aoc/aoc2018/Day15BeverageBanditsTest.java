package aoc.aoc2018;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day15BeverageBanditsTest {
    @ParameterizedTest
    @CsvSource({
            "27730, day15-demo0.txt",
            "36334, day15-demo1.txt",
            "39514, day15-demo2.txt",
            "27755, day14-demo3.txt",
            "28944, day15-demo4.txt",
            "18740, day15-demo5.txt",
            "226688, day15.txt"
    })
    void ComputeCombatOutcome(int outcome, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/2018/" + fileName))));
        Day15BeverageBandits day15BeverageBandits = new Day15BeverageBandits();
        assertEquals(outcome, day15BeverageBandits.computeCombatOutcome(input));
    }

    @ParameterizedTest
    @CsvSource({
            "4988, day15-demo0.txt",
            "31284, day15-demo6.txt",
            "3478, day14-demo3.txt",
            "6474, day15-demo4.txt",
            "1140, day15-demo5.txt",
            "62958, day15.txt"
    })
    void ComputeCombatOutcomeNoDeadElves(int outcome, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/2018/" + fileName))));
        Day15BeverageBandits day15BeverageBandits = new Day15BeverageBandits();
        assertEquals(outcome, day15BeverageBandits.computeCombatOutcomeNoDeadElves(input));
    }

}