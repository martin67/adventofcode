package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 2: Rock Paper Scissors")
class Day2RockPaperScissorsTest {

    @ParameterizedTest
    @CsvSource({"15, src/test/resources/2022/day2-demo1.txt",
            "11873, src/test/resources/2022/day2.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day2RockPaperScissors(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"12, src/test/resources/2022/day2-demo1.txt",
            "12014, src/test/resources/2022/day2.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day2RockPaperScissors(inputLines).problem2())
                .isEqualTo(expected);
    }

}