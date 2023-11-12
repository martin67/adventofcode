package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 16: Proboscidea Volcanium")
class Day16ProboscideaVolcaniumTest {

    @ParameterizedTest
    @CsvSource({"1651, src/test/resources/day16-demo1.txt",
            "7763, src/test/resources/day16.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day16ProboscideaVolcanium(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"70, src/test/resources/day16-demo1.txt",
            "2569, src/test/resources/day16.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day16ProboscideaVolcanium(inputLines).problem2())
                .isEqualTo(expected);
    }

}