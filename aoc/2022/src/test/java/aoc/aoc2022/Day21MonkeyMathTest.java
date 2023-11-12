package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 21: Monkey Math")
class Day21MonkeyMathTest {

    @ParameterizedTest
    @CsvSource({"152, src/test/resources/day21-demo1.txt",
            "80326079210554, src/test/resources/day21.txt"})
    void problem1(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day21MonkeyMath(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({ //"301, src/test/resources/day21-demo1.txt",
            "3617613952378, src/test/resources/day21.txt"})
    void problem2(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day21MonkeyMath(inputLines).problem2())
                .isEqualTo(expected);
    }

}