package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 7: Amplification Circuit")
class Day7AmplificationCircuitTest {

    @ParameterizedTest
    @CsvSource({"43210, day7-demo1.txt",
            "54321, day7-demo2.txt",
            "65210, day7-demo3.txt",
            "277328, day7.txt"})
    void problem1(int expected, String fileName) throws IOException, InterruptedException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day7AmplificationCircuit(inputLines).highestSignal()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"139629729, day7-demo4.txt",
            "18216, day7-demo5.txt",
            "11304734, day7.txt"})
    void problem2(int expected, String fileName) throws IOException, InterruptedException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day7AmplificationCircuit(inputLines).highestSignalFeedbackLoop()).isEqualTo(expected);
    }
}