package aoc2019;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day9SensorBoostTest {
    @ParameterizedTest
    @CsvSource({"0, 0, '109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99'",
            "0, 1, '1102,34915192,34915192,7,4,7,99,0'",
            "0, 1, '104,1125899906842624,99'"})
    void boostTesting(int expected, Integer phaseSetting, String inputLine) throws InterruptedException {
        assertEquals(expected, new Day9SensorBoost().boostKeycode(inputLine, phaseSetting));
    }

    @Test
    void boostKeycode() throws Exception {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day9.txt"));
        assertEquals(0, new Day9SensorBoost().boostKeycode(inputLines.get(0), 1));
    }
}