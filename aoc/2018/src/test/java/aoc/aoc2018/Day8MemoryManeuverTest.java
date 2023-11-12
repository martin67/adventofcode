package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 8: Memory Maneuver")
class Day8MemoryManeuverTest {

    @ParameterizedTest
    @CsvSource({"138, src/test/resources/day8-demo1.txt",
            "37439, src/test/resources/day8.txt"})
    void problem1(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day8MemoryManeuver day8MemoryManeuver = new Day8MemoryManeuver();
        assertThat(day8MemoryManeuver.computeSumOfMetadata(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"66, src/test/resources/day8-demo1.txt",
            "20815, src/test/resources/day8.txt"})
    void problem2(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day8MemoryManeuver day8MemoryManeuver = new Day8MemoryManeuver();
        assertThat(day8MemoryManeuver.computeRootNode(input)).isEqualTo(expected);
    }
}