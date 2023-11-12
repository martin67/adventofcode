package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 14: Reindeer Olympics")
class Day14ReindeerOlympicsTest {

    @ParameterizedTest
    @CsvSource({"1120, 1000, src/test/resources/day14-demo1.txt",
            "2640, 2503, src/test/resources/day14.txt"})
    void problem1(int expected, int time, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day14ReindeerOlympics(inputLines).distanceTraveled(time))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"689, 1000, src/test/resources/day14-demo1.txt",
            "1102, 2503, src/test/resources/day14.txt"})
    void problem2(int expected, int time, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day14ReindeerOlympics(inputLines).winningPoints(time))
                .isEqualTo(expected);
    }
}