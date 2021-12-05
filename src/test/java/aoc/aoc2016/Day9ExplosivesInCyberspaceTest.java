package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2016: Day 9: Explosives in Cyberspace")
class Day9ExplosivesInCyberspaceTest {

    @ParameterizedTest
    @CsvSource({"6, src/test/resources/2016/day9-demo1.txt",
            "7, src/test/resources/2016/day9-demo2.txt",
            "9, src/test/resources/2016/day9-demo3.txt",
            "11, src/test/resources/2016/day9-demo4.txt",
            "6, src/test/resources/2016/day9-demo5.txt",
            "18, src/test/resources/2016/day9-demo6.txt",
            "70186, src/test/resources/2016/day9.txt"})
    void problem1(long expected, String fileName) throws IOException {
        assertEquals(expected, new Day9ExplosivesInCyberspace(fileName).decompressedVersion1());
    }

    @ParameterizedTest
    @CsvSource({"9, src/test/resources/2016/day9-demo3.txt",
            "20, src/test/resources/2016/day9-demo6.txt",
            "241920, src/test/resources/2016/day9-demo7.txt",
            "445, src/test/resources/2016/day9-demo8.txt",
            "10915059201, src/test/resources/2016/day9.txt"})
    void problem2(long expected, String fileName) throws IOException {
        assertEquals(expected, new Day9ExplosivesInCyberspace(fileName).decompressedVersion2());
    }

}