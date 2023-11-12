package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 21: Dirac Dice")
class Day21DiracDiceTest {

    @ParameterizedTest
    @CsvSource({"739785, src/test/resources/day21-demo1.txt",
            "707784, src/test/resources/day21.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day21DiracDice(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"0, src/test/resources/day21-demo1.txt",
            "0, src/test/resources/day21.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day21DiracDice(inputLines).problem2());
    }

}