package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2020: Day 22: Crab Combat")
class Day22CrabCombatTest {

    @ParameterizedTest
    @CsvSource({"306, src/test/resources/2020/day22-demo1.txt",
            "30197, src/test/resources/2020/day22.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day22CrabCombat(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"291, src/test/resources/2020/day22-demo1.txt",
            "105, src/test/resources/2020/day22-demo2.txt",
            "34031, src/test/resources/2020/day22.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day22CrabCombat(inputLines).problem2());
    }

}