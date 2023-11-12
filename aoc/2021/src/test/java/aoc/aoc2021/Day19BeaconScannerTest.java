package aoc.aoc2021;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2021: Day 19: Beacon Scanner")
class Day19BeaconScannerTest {

    @ParameterizedTest
    @CsvSource({"6, day19-demo1.txt",
            "79, day19-demo2.txt",
            "0, day19.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day19BeaconScanner(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"0, day19-demo1.txt",
            "0, day19.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day19BeaconScanner(inputLines).problem2()).isEqualTo(expected);
    }
}