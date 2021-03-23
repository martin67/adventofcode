package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 9: Encoding Error")
class Day9EncodingErrorTest {

    @ParameterizedTest
    @CsvSource({"127, 5, src/test/resources/2020/day9-demo1.txt",
            "373803594, 25, src/test/resources/2020/day9.txt"})
    void invalidNumber(long expected, int preamble, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day9EncodingError(preamble, inputLines).invalidNumber());
    }

    @ParameterizedTest
    @CsvSource({"62, 5, src/test/resources/2020/day9-demo1.txt",
            "51152360, 25, src/test/resources/2020/day9.txt"})
    void encryptionWeakness(long expected, int preamble, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day9EncodingError(preamble, inputLines).encryptionWeakness());
    }

}
