package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 9: Sensor Boost")
class Day9SensorBoostTest {

    @Test
    void boostTest1() throws InterruptedException {
        String input = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99";
        String output = String.join(",", new Day9SensorBoost().boostKeyCode(input, 0));
        assertEquals(input, output);
    }

    @Test
    void boostTest2() throws InterruptedException {
        String input = "1102,34915192,34915192,7,4,7,99,0";
        List<String> output = new Day9SensorBoost().boostKeyCode(input, 0);
        assertEquals(16, output.get(0).length());
    }

    @Test
    void boostTest3() throws InterruptedException {
        String input = "104,1125899906842624,99";
        List<String> output = new Day9SensorBoost().boostKeyCode(input, 0);
        assertEquals("1125899906842624", output.get(0));
    }

    @Test
    void problem1() throws InterruptedException, IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day9.txt"));
        List<String> output = new Day9SensorBoost().boostKeyCode(inputLines.get(0), 1);
        assertEquals("2494485073", output.get(0));
    }

    @Test
    void problem2() throws InterruptedException, IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day9.txt"));
        List<String> output = new Day9SensorBoost().boostKeyCode(inputLines.get(0), 2);
        assertEquals("44997", output.get(0));
    }

}