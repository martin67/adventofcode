package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 20: Grove Positioning System")
class Day20GrovePositioningSystemTest {

    @ParameterizedTest
    @CsvSource({"3, src/test/resources/day20-demo1.txt",
            "7395, src/test/resources/day20.txt"})
    void problem1(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day20GrovePositioningSystem(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1623178306, src/test/resources/day20-demo1.txt",
            "1640221678213, src/test/resources/day20.txt"})
    void problem2(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day20GrovePositioningSystem(inputLines).problem2())
                .isEqualTo(expected);
    }

}