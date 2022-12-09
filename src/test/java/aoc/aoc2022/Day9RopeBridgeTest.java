package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 9: Rope Bridge")
class Day9RopeBridgeTest {

    @ParameterizedTest
    @CsvSource({"13, src/test/resources/2022/day9-demo1.txt",
            "6354, src/test/resources/2022/day9.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day9RopeBridge(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1, src/test/resources/2022/day9-demo1.txt",
            "36, src/test/resources/2022/day9-demo2.txt",
            "2651, src/test/resources/2022/day9.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day9RopeBridge(inputLines).problem2())
                .isEqualTo(expected);
    }

}