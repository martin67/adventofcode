package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 18: Operation Order")
class Day18OperationOrderTest {

    @ParameterizedTest
    @CsvSource({"26457, day18-demo1.txt",
            "36382392389406, day18.txt"})
    void problem1(long expected, String fileName) throws Exception {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18OperationOrder(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"694173, day18-demo1.txt",
            "381107029777968, day18.txt"})
    void problem2(long expected, String fileName) throws Exception {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18OperationOrder(inputLines).problem2()).isEqualTo(expected);
    }
}