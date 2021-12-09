package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("2017: Day 18: Duet")
class Day18DuetTest {

    @ParameterizedTest
    @CsvSource({"4, src/test/resources/2017/day18-demo1.txt",
            "9423, src/test/resources/2017/day18.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day18Duet(Files.readAllLines(Paths.get(fileName))).problem1());
    }

    @ParameterizedTest
    @CsvSource({"3, src/test/resources/2017/day18-demo2.txt",
            "0, src/test/resources/2017/day18.txt"})
    void problem2(int expected, String fileName) throws IOException, ExecutionException, InterruptedException {
        assertEquals(expected, new Day18Duet(Files.readAllLines(Paths.get(fileName))).problem2());
    }

}