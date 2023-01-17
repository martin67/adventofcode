package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 17: Pyroclastic Flow")
class Day17PyroclasticFlowTest {

    @ParameterizedTest
    @CsvSource({"3068, src/test/resources/2022/day17-demo1.txt",
            "3127, src/test/resources/2022/day17.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day17PyroclasticFlow(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1514285714288, src/test/resources/2022/day17-demo1.txt",
            "2569, src/test/resources/2022/day17.txt"})
    void problem2(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day17PyroclasticFlow(inputLines).problem23())
                .isEqualTo(expected);
    }

    // 1543937217650 too high
}