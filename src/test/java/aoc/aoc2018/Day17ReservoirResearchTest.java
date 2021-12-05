package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("2018: Day 17: Reservoir Research")
class Day17ReservoirResearchTest {

    @ParameterizedTest
    @CsvSource({"57, src/test/resources/2018/day17-demo1.txt",
            "39162, src/test/resources/2018/day17.txt"})
    void problem1(int outcome, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day17ReservoirResearch day17ReservoirResearch = new Day17ReservoirResearch();
        assertEquals(outcome, day17ReservoirResearch.numberOfWaterTiles(input));
    }

    @ParameterizedTest
    @CsvSource({"29, src/test/resources/2018/day17-demo1.txt",
            "32047, src/test/resources/2018/day17.txt"})
    void problem2(int outcome, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day17ReservoirResearch day17ReservoirResearch = new Day17ReservoirResearch();
        assertEquals(outcome, day17ReservoirResearch.numberOfRemainingWater(input));
    }

}