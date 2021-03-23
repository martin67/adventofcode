package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 20: Jurassic Jigsaw")
class Day20JurassicJigsawTest {

    @ParameterizedTest
    @CsvSource({"20899048083289, src/test/resources/2020/day20-demo1.txt",
            "255, src/test/resources/2020/day20.txt"})
    void problem1(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day20JurassicJigsaw(inputLines).problem1());
    }

}