package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 9: Sensor Boost")
class Day9SensorBoostTest {

    @Test
    void boostTest1() throws InterruptedException {
        String input = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99";
        String output = String.join(",", new Day9SensorBoost().boostKeyCode(input, 0));
        assertThat(output).isEqualTo(input);
    }

    @Test
    void boostTest2() throws InterruptedException {
        String input = "1102,34915192,34915192,7,4,7,99,0";
        var output = new Day9SensorBoost().boostKeyCode(input, 0);
        assertThat(output.get(0)).hasSize(16);
    }

    @Test
    void boostTest3() throws InterruptedException {
        String input = "104,1125899906842624,99";
        var output = new Day9SensorBoost().boostKeyCode(input, 0);
        assertThat(output.get(0)).isEqualTo("1125899906842624");
    }

    @Test
    void problem1() throws InterruptedException, IOException {
        var inputLines = AocFiles.readAllLines("day9.txt");
        var output = new Day9SensorBoost().boostKeyCode(inputLines.get(0), 1);
        assertThat(output.get(0)).isEqualTo("2494485073");
    }

    @Test
    void problem2() throws InterruptedException, IOException {
        var inputLines = AocFiles.readAllLines("day9.txt");
        var output = new Day9SensorBoost().boostKeyCode(inputLines.get(0), 2);
        assertThat(output.get(0)).isEqualTo("44997");
    }
}