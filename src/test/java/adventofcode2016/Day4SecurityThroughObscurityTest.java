package adventofcode2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4SecurityThroughObscurityTest {

    @ParameterizedTest
    @CsvSource({"1514, out/test/resources/2016/day4-demo1.txt",
    "0, out/test/resources/2016/day4.txt"})
    void sectorIdSum(long expected, String fileName) throws IOException {
        assertEquals(expected, new Day4SecurityThroughObscurity(fileName).sectorIdSum());
    }

}