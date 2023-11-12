package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 8: I Heard You Like Registers")
class Day8IHeardYouLikeRegistersTest {

    @ParameterizedTest
    @CsvSource({"1, day8-demo1.txt",
            "3745, day8.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertThat(new Day8IHeardYouLikeRegisters(AocFiles.readAllLines(fileName)).largestRegisterValue(false))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"10, day8-demo1.txt",
            "4644, day8.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertThat(new Day8IHeardYouLikeRegisters(AocFiles.readAllLines(fileName)).largestRegisterValue(true))
                .isEqualTo(expected);
    }
}