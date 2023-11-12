package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 2: Corruption Checksum")
class Day2CorruptionChecksumTest {

    @ParameterizedTest
    @CsvSource({"18, day2-demo1.txt",
            "32121, day2.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertThat(new Day2CorruptionChecksum().computeChecksum(AocFiles.readAllLines(fileName))).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"9, day2-demo2.txt",
            "197, day2.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertThat(new Day2CorruptionChecksum().computeDivisibleChecksum(AocFiles.readAllLines(fileName)))
                .isEqualTo(expected);
    }
}