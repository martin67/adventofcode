package aoc.aoc2024;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2024: Day 10: Hoof It")
class Day10HoofItTest {

    @ParameterizedTest
    @CsvSource({"1, day10-demo1.txt",
            "2, day10-demo2.txt",
            "4, day10-demo3.txt",
            "3, day10-demo4.txt",
            "36, day10-demo5.txt",
            "688, day10.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day10HoofIt(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"3, day10-demo6.txt",
            "13, day10-demo7.txt",
            "227, day10-demo8.txt",
            "81, day10-demo5.txt",
            "1459, day10.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day10HoofIt(inputLines).problem2()).isEqualTo(expected);
    }
}