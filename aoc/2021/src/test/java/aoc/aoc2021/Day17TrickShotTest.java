package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 17: Trick Shot")
class Day17TrickShotTest {

    @ParameterizedTest
    @CsvSource({"3, 7, 2",
            "6, 6, 3",
            "0, 9, 0",
            "-4, 17, -4"})
    void problem0(int expected, int xVelocity, int yVelocity) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/day17-demo1.txt"));
        assertEquals(expected, new Day17TrickShot(inputLines).problem0(xVelocity, yVelocity));
    }

    @ParameterizedTest
    @CsvSource({"45, src/test/resources/day17-demo1.txt",
            "12090, src/test/resources/day17.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day17TrickShot(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"112, src/test/resources/day17-demo1.txt",
            "5059, src/test/resources/day17.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day17TrickShot(inputLines).problem2());
    }

}