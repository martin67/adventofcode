package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 22: Sporifica Virus")
class Day22SporificaVirusTest {

    @ParameterizedTest
    @CsvSource({"5, 7, day22-demo1.txt",
            "41, 70, day22-demo1.txt",
            "5587, 10000, day22-demo1.txt",
            "5348, 10000, day22.txt"})
    void problem1(int expected, int bursts, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day22SporificaVirus(inputLines).problem1(bursts)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"26, 100, day22-demo1.txt",
            "2511944, 10000000, day22-demo1.txt",
            "2512225, 10000000, day22.txt"})
    void problem2(int expected, int bursts, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day22SporificaVirus(inputLines).problem2(bursts)).isEqualTo(expected);
    }
}