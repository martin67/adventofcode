package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 16: Aunt Sue")
class Day16AuntSueTest {

    @ParameterizedTest
    @CsvSource({"213, src/test/resources/day16.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day16AuntSue(inputLines).sueNumber())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"323, src/test/resources/day16.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day16AuntSue(inputLines).realSueNumber())
                .isEqualTo(expected);
    }
}