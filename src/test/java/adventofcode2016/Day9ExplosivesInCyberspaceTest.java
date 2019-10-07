package adventofcode2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9ExplosivesInCyberspaceTest {

    @ParameterizedTest
    @CsvSource({"6, out/test/resources/2016/day9-demo1.txt",
            "7, out/test/resources/2016/day9-demo2.txt",
            "9, out/test/resources/2016/day9-demo3.txt",
            "11, out/test/resources/2016/day9-demo4.txt",
            "6, out/test/resources/2016/day9-demo5.txt",
            "18, out/test/resources/2016/day9-demo6.txt",
            "70186, out/test/resources/2016/day9.txt"})
    void decompressedVersion1(long expected, String fileName) throws IOException {
        assertEquals(expected, new Day9ExplosivesInCyberspace(fileName).decompressedVersion1());
    }

    @ParameterizedTest
    @CsvSource({"9, out/test/resources/2016/day9-demo3.txt",
            "20, out/test/resources/2016/day9-demo6.txt",
            "241920, out/test/resources/2016/day9-demo7.txt",
            "445, out/test/resources/2016/day9-demo8.txt",
            "10915059201, out/test/resources/2016/day9.txt"})
    void decompressedVersion2(long expected, String fileName) throws IOException {
        assertEquals(expected, new Day9ExplosivesInCyberspace(fileName).decompressedVersion2());
    }

}