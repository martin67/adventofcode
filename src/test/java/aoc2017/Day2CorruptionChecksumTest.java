package aoc2017;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2CorruptionChecksumTest {

    @ParameterizedTest
    @CsvSource({"18, src/test/resources/2017/day2-demo1.txt",
            "32121, src/test/resources/2017/day2.txt"})
    void computeChecksum(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day2CorruptionChecksum().computeChecksum(Files.readAllLines(Paths.get(fileName))));
    }

    @ParameterizedTest
    @CsvSource({"9, src/test/resources/2017/day2-demo2.txt",
            "197, src/test/resources/2017/day2.txt"})
    void computeDivisibleChecksum(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day2CorruptionChecksum().computeDivisibleChecksum(Files.readAllLines(Paths.get(fileName))));
    }

}