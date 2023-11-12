package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2017: Day 2: Corruption Checksum")
class Day2CorruptionChecksumTest {

    @ParameterizedTest
    @CsvSource({"18, src/test/resources/day2-demo1.txt",
            "32121, src/test/resources/day2.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day2CorruptionChecksum().computeChecksum(Files.readAllLines(Paths.get(fileName))));
    }

    @ParameterizedTest
    @CsvSource({"9, src/test/resources/day2-demo2.txt",
            "197, src/test/resources/day2.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day2CorruptionChecksum().computeDivisibleChecksum(Files.readAllLines(Paths.get(fileName))));
    }

}