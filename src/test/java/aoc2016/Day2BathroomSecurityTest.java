package aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2BathroomSecurityTest {

    @ParameterizedTest
    @CsvSource({"1985, src/test/resources/2016/day2-demo1.txt",
            "61529, src/test/resources/2016/day2.txt"})
    void bathroomCode(String expected, String fileName) throws IOException {
        assertEquals(expected, new Day2BathroomSecurity(fileName).bathroomCode());
    }

    @ParameterizedTest
    @CsvSource({"5DB3, src/test/resources/2016/day2-demo1.txt",
            "C2C28, src/test/resources/2016/day2.txt"})
    void secondBathroomCode(String expected, String fileName) throws IOException {
        assertEquals(expected, new Day2BathroomSecurity(fileName).secondBathroomCode());
    }
}