package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 17: Reservoir Research")
class Day17ReservoirResearchTest {

    @ParameterizedTest
    @CsvSource({"57, src/test/resources/day17-demo1.txt",
            "39162, src/test/resources/day17.txt"})
    void problem1(int outcome, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day17ReservoirResearch day17ReservoirResearch = new Day17ReservoirResearch();
        assertThat(day17ReservoirResearch.numberOfWaterTiles(input)).isEqualTo(outcome);
    }

    @ParameterizedTest
    @CsvSource({"29, src/test/resources/day17-demo1.txt",
            "32047, src/test/resources/day17.txt"})
    void problem2(int outcome, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day17ReservoirResearch day17ReservoirResearch = new Day17ReservoirResearch();
        assertThat(day17ReservoirResearch.numberOfRemainingWater(input)).isEqualTo(outcome);
    }
}