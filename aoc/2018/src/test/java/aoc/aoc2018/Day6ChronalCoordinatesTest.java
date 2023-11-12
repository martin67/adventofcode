package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 6: Chronal Coordinates")
class Day6ChronalCoordinatesTest {

    @ParameterizedTest
    @CsvSource({"17, src/test/resources/day6-demo1.txt",
            "3293, src/test/resources/day6.txt"})
    void problem1(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day6ChronalCoordinates day6ChronalCoordinates = new Day6ChronalCoordinates();
        assertThat(day6ChronalCoordinates.largestArea(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"16, 32, src/test/resources/day6-demo1.txt",
            "45176, 10000, src/test/resources/day6.txt"})
    void problem2(int expected, int totalDistance, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day6ChronalCoordinates day6ChronalCoordinates = new Day6ChronalCoordinates();
        assertThat(day6ChronalCoordinates.sizeOfRegion(input, totalDistance)).isEqualTo(expected);
    }
}