package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 4: Camp Cleanup")
class Day4CampCleanupTest {

    @ParameterizedTest
    @CsvSource({"2, src/test/resources/2022/day4-demo1.txt",
            "532, src/test/resources/2022/day4.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day4CampCleanup(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"4, src/test/resources/2022/day4-demo1.txt",
            "854, src/test/resources/2022/day4.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day4CampCleanup(inputLines).problem2())
                .isEqualTo(expected);
    }

}