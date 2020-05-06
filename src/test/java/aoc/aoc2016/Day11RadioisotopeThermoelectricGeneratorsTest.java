package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11RadioisotopeThermoelectricGeneratorsTest {

    @ParameterizedTest
    @CsvSource({"11, src/test/resources/2016/day11-demo1.txt",
            "37, src/test/resources/2016/day11.txt"})
    void minimumNumberOfSteps(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day11RadioisotopeThermoelectricGenerators(fileName).minimumNumberOfSteps());
    }

}