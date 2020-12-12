package aoc.aoc2017;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day16PermutationPromenadeTest {

    @ParameterizedTest
    @CsvSource({"baedc, 5, src/test/resources/2017/day16-demo1.txt",
            "glnacbhedpfjkiom, 16, src/test/resources/2017/day16.txt"})
    void danceOrder(String expected, int programSize, String fileName) throws IOException {
        assertEquals(expected, new Day16PermutationPromenade(Files.readAllLines(Paths.get(fileName)), programSize).danceOrder());
    }

    @ParameterizedTest
    @CsvSource({"baedc, 5, src/test/resources/2017/day16-demo1.txt",
            "fmpanloehgkdcbji, 16, src/test/resources/2017/day16.txt"})
    void secondDanceOrder(String expected, int programSize, String fileName) throws IOException {
        assertEquals(expected, new Day16PermutationPromenade(Files.readAllLines(Paths.get(fileName)), programSize).secondDanceOrder());
    }

}
