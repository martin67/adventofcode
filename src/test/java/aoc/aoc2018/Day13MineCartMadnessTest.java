package aoc.aoc2018;

import aoc.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2018: Day 13: Mine Cart Madness")
class Day13MineCartMadnessTest {

    @ParameterizedTest
    @CsvSource({"7, 3, src/test/resources/2018/day13-demo1.txt",
            "86, 118, src/test/resources/2018/day13.txt"})
    void problem1(int x, int y, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day13MineCartMadness day13MineCartMadness = new Day13MineCartMadness(input);
        assertEquals(new Position(x, y), day13MineCartMadness.getFirstCollision());
    }

    @ParameterizedTest
    @CsvSource({"6, 4, src/test/resources/2018/day13-demo2.txt",
            "2, 81, src/test/resources/2018/day13.txt"})
    void problem2(int x, int y, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day13MineCartMadness day13MineCartMadness = new Day13MineCartMadness(input);
        assertEquals(new Position(x, y), day13MineCartMadness.lastCart());
    }

}