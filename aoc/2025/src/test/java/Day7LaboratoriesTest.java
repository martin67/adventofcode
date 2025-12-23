import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

@DisplayName("2024: Day 7: Laboratories")
class Day7LaboratoriesTest {

    @ParameterizedTest
    @CsvSource({"21, day7-demo1.txt",
            "1537, day7.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        Assertions.assertThat(new Day7Laboratories(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"40, day7-demo1.txt",
            "0, day7.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        Assertions.assertThat(new Day7Laboratories(inputLines).problem2()).isEqualTo(expected);
    }
}