package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 11: Monkey in the Middle")
class Day11MonkeyInTheMiddleTest {

    @ParameterizedTest
    @CsvSource({"10605, src/test/resources/day11-demo1.txt",
            "120756, src/test/resources/day11.txt"})
    void problem1(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day11MonkeyInTheMiddle(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2713310158, src/test/resources/day11-demo1.txt",
            "39109444654, src/test/resources/day11.txt"})
    void problem2(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day11MonkeyInTheMiddle(inputLines).problem2())
                .isEqualTo(expected);
    }

}