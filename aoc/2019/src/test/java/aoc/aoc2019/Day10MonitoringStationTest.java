package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 10: Monitoring Station")
class Day10MonitoringStationTest {

    @ParameterizedTest
    @CsvSource({"8, day10-demo1.txt",
            "33, day10-demo2.txt",
            "35, day10-demo3.txt",
            "41, day10-demo4.txt",
            "210, day10-demo5.txt",
            "299, day10.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day10MonitoringStation(inputLines).bestLocation()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"802, day10-demo5.txt",
            "1419, day10.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day10MonitoringStation(inputLines).vaporize()).isEqualTo(expected);
    }
}