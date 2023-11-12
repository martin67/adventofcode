package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 6: Tuning Trouble")
class Day6TuningTroubleTest {

    @ParameterizedTest
    @CsvSource({"7, day6-demo1.txt",
            "5, day6-demo2.txt",
            "6, day6-demo3.txt",
            "10, day6-demo4.txt",
            "11, day6-demo5.txt",
            "1531, day6.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day6TuningTrouble(inputLines).problem(4)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"19, day6-demo1.txt",
            "23, day6-demo2.txt",
            "23, day6-demo3.txt",
            "29, day6-demo4.txt",
            "26, day6-demo5.txt",
            "2518, day6.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day6TuningTrouble(inputLines).problem(14)).isEqualTo(expected);
    }
}