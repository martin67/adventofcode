package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 7: Some Assembly Required")
class Day7SomeAssemblyRequiredTest {
    @ParameterizedTest
    @CsvSource({"72, d, src/test/resources/day7-demo1.txt",
            "46065, a, src/test/resources/day7.txt"})
    void problem1(int expected, String wireName, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day7SomeAssemblyRequired(inputLines).signalWire(wireName))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"14134, a, src/test/resources/day7.txt"})
    void problem2(int expected, String wireName, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day7SomeAssemblyRequired(inputLines).signalRewired(wireName))
                .isEqualTo(expected);
    }
}