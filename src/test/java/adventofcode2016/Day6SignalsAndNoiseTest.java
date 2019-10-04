package adventofcode2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6SignalsAndNoiseTest {

    @ParameterizedTest
    @CsvSource({"easter, out/test/resources/2016/day6-demo1.txt",
            "0, out/test/resources/2016/day6.txt"})
    void correctedVersion(String expected, String fileName) throws IOException {
        assertEquals(expected, new Day6SignalsAndNoise(fileName).correctedVersion());
    }

}