package aoc.aoc2021;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2021: Day 16: Packet Decoder")
class Day16PacketDecoderTest {

    @ParameterizedTest
    @CsvSource({"6, D2FE28",
            "9, 38006F45291200",
            "14, EE00D40C823060",
            "16, 8A004A801A8002F478",
            "12, 620080001611562C8802118E34",
            "23, C0015000016115A2E0802F182340",
            "31, A0016C880162017C3686B18A3D4780"})
    void problem11(int expected, String input) {
        assertThat(new Day16PacketDecoder(List.of(input)).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1014, day16.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day16PacketDecoder(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"3, C200B40A82",
            "54, 04005AC33890",
            "7, 880086C3E88112",
            "9, CE00C43D881120",
            "1, D8005AC2A8F0",
            "0, F600BC2D8F",
            "0, 9C005AC2F8F0",
            "1, 9C0141080250320F1802104A08"})
    void problem21(int expected, String input) {
        assertThat(new Day16PacketDecoder(List.of(input)).problem2()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1922490999789, day16.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day16PacketDecoder(inputLines).problem2()).isEqualTo(expected);
    }
}