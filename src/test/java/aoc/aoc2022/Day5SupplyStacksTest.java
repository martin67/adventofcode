package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 5: Supply Stacks")
class Day5SupplyStacksTest {

    @ParameterizedTest
    @CsvSource({"CMZ, src/test/resources/2022/day5-demo1.txt",
            "CNSZFDVLJ, src/test/resources/2022/day5.txt"})
    void problem1(String expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day5SupplyStacks(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"MCD, src/test/resources/2022/day5-demo1.txt",
            "QNDWLMGNS, src/test/resources/2022/day5.txt"})
    void problem2(String expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day5SupplyStacks(inputLines).problem2())
                .isEqualTo(expected);
    }

}