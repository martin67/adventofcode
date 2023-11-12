package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import aoc.common.Position;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 13: Mine Cart Madness")
class Day13MineCartMadnessTest {

    @ParameterizedTest
    @CsvSource({"7, 3, src/test/resources/day13-demo1.txt",
            "86, 118, src/test/resources/day13.txt"})
    void problem1(int x, int y, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day13MineCartMadness day13MineCartMadness = new Day13MineCartMadness(input);
        assertThat(day13MineCartMadness.getFirstCollision()).isEqualTo(new Position(x, y));
    }

    @ParameterizedTest
    @CsvSource({"6, 4, src/test/resources/day13-demo2.txt",
            "2, 81, src/test/resources/day13.txt"})
    void problem2(int x, int y, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day13MineCartMadness day13MineCartMadness = new Day13MineCartMadness(input);
        assertThat(day13MineCartMadness.lastCart()).isEqualTo(new Position(x, y));
    }
}