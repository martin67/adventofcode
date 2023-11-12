package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 13: Packet Scanners")
class Day13PacketScannersTest {

    @ParameterizedTest
    @CsvSource({"24, day13-demo1.txt",
            "1928, day13.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day13PacketScanners(inputLines).tripSeverity()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"10, day13-demo1.txt",
            "3830344, day13.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day13PacketScanners(inputLines).packetDelay2()).isEqualTo(expected);
    }
}
