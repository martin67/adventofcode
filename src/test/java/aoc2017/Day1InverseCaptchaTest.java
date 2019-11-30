package aoc2017;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day1InverseCaptchaTest {

    @ParameterizedTest
    @CsvSource({"3, 1122",
            "4, 1111",
            "0, 1234",
            "9, 91212129"})
    void computeCaptcha(int checksum, String sequence) {
        assertEquals(checksum, new Day1InverseCaptcha().computeCaptcha(sequence));
    }

    @Test
    void computeCaptchaFromFile() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2017/day1.txt"));
        for (String line : inputLines) {
            assertEquals(995, new Day1InverseCaptcha().computeCaptcha(line));
        }
    }

    @ParameterizedTest
    @CsvSource({"6, 1212",
            "0, 1221",
            "4, 123425",
            "12, 123123",
            "4, 12131415"})
    void computeHalfwayCaptcha(int checksum, String sequence) {
        assertEquals(checksum, new Day1InverseCaptcha().computeHalfwayCaptcha(sequence));
    }

    @Test
    void computeHalfwayCaptchaFromFile() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2017/day1.txt"));
        for (String line : inputLines) {
            assertEquals(1130, new Day1InverseCaptcha().computeHalfwayCaptcha(line));
        }
    }

}