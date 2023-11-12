package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 1: Inverse Captcha")
class Day1InverseCaptchaTest {

    @ParameterizedTest
    @CsvSource({"3, 1122",
            "4, 1111",
            "0, 1234",
            "9, 91212129"})
    void computeCaptcha(int checksum, String sequence) {
        assertThat(new Day1InverseCaptcha().computeCaptcha(sequence)).isEqualTo(checksum);
    }

    @Test
    void problem1() throws IOException {
        var inputLines = AocFiles.readAllLines("day1.txt");
        for (String line : inputLines) {
            assertThat(new Day1InverseCaptcha().computeCaptcha(line)).isEqualTo(995);
        }
    }

    @ParameterizedTest
    @CsvSource({"6, 1212",
            "0, 1221",
            "4, 123425",
            "12, 123123",
            "4, 12131415"})
    void computeHalfwayCaptcha(int checksum, String sequence) {
        assertThat(new Day1InverseCaptcha().computeHalfwayCaptcha(sequence)).isEqualTo(checksum);
    }

    @Test
    void problem2() throws IOException {
        var inputLines = AocFiles.readAllLines("day1.txt");
        for (String line : inputLines) {
            assertThat(new Day1InverseCaptcha().computeHalfwayCaptcha(line)).isEqualTo(1130);
        }
    }
}