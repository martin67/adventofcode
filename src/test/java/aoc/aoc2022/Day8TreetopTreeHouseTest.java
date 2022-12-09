package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 8: Treetop Tree House")
class Day8TreetopTreeHouseTest {

    @ParameterizedTest
    @CsvSource({"21, src/test/resources/2022/day8-demo1.txt",
            "1782, src/test/resources/2022/day8.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day8TreetopTreeHouse(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"8, src/test/resources/2022/day8-demo1.txt",
            "474606, src/test/resources/2022/day8.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day8TreetopTreeHouse(inputLines).problem2())
                .isEqualTo(expected);
    }

}