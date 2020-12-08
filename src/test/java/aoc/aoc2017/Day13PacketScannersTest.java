package aoc.aoc2017;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13PacketScannersTest {

    @ParameterizedTest
    @CsvSource({"24, src/test/resources/2017/day13-demo1.txt",
            "1928, src/test/resources/2017/day13.txt"})
    void tripSeverity(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day13PacketScanners(Files.readAllLines(Paths.get(fileName))).tripSeverity());
    }

    @ParameterizedTest
    @CsvSource({"10, src/test/resources/2017/day13-demo1.txt",
            "3830344, src/test/resources/2017/day13.txt"})
    void delay(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day13PacketScanners(Files.readAllLines(Paths.get(fileName))).packetDelay2());
    }

}
