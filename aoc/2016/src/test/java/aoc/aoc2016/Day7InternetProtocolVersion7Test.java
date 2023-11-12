package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2016: Day 7: Internet Protocol Version 7")
class Day7InternetProtocolVersion7Test {

    @ParameterizedTest
    @CsvSource({"2, src/test/resources/day7-demo1.txt",
            "115, src/test/resources/day7.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day7InternetProtocolVersion7(fileName).supportTLS());
    }

    @ParameterizedTest
    @CsvSource({"3, src/test/resources/day7-demo2.txt",
            "231, src/test/resources/day7.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day7InternetProtocolVersion7(fileName).supportSSL());
    }

}