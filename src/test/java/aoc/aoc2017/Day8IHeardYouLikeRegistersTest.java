package aoc.aoc2017;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8IHeardYouLikeRegistersTest {

    @ParameterizedTest
    @CsvSource({"1, src/test/resources/2017/day8-demo1.txt",
            "3745, src/test/resources/2017/day8.txt"})
    void largestRegisterValue(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day8IHeardYouLikeRegisters(Files.readAllLines(Paths.get(fileName))).largestRegisterValue(false));
    }

    @ParameterizedTest
    @CsvSource({"10, src/test/resources/2017/day8-demo1.txt",
            "4644, src/test/resources/2017/day8.txt"})
    void largestRegisterValueEver(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day8IHeardYouLikeRegisters(Files.readAllLines(Paths.get(fileName))).largestRegisterValue(true));
    }

}