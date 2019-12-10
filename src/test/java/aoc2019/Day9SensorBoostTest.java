package aoc2019;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day9SensorBoostTest {

    @Test
    void boostTest1() throws InterruptedException {
        String input = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99";
        String output = new Day9SensorBoost().boostKeycode(input, 0)
                .map(BigInteger::toString)
                .collect(Collectors.joining(","));
        assertEquals(input, output);
    }

    @Test
    void boostTest2() throws InterruptedException {
        String input = "1102,34915192,34915192,7,4,7,99,0";
        String output = new Day9SensorBoost().boostKeycode(input, 1).findFirst().toString();
        assertEquals(16, output.length());
    }

    @Test
    void boostTest3(int expected, Integer phaseSetting, String inputLine) throws InterruptedException {
        String input = "104,1125899906842624,99";
        String output = new Day9SensorBoost().boostKeycode(input, 1).findFirst().toString();
        assertEquals("1125899906842624", output);
    }


    @Test
    void boostKeycode() throws Exception {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day9.txt"));
        assertEquals(0, new Day9SensorBoost().boostKeycode(inputLines.get(0), 1));
    }
}