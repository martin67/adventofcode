package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 22: Monkey Map")
class Day22MonkeyMapTest {

    @ParameterizedTest
    @CsvSource({"6032, src/test/resources/day22-demo1.txt",
            "13566, src/test/resources/day22.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day22MonkeyMap(inputLines).problem1(false))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"5031, src/test/resources/day22-demo1.txt",
            "2569, src/test/resources/day22.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day22MonkeyMap(inputLines).problem2())
                .isEqualTo(expected);
    }

}