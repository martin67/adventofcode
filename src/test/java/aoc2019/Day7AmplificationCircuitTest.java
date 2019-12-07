package aoc2019;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7AmplificationCircuitTest {

    @ParameterizedTest
    @CsvSource({"43210, src/test/resources/2019/day7-demo1.txt",
            "54321, src/test/resources/2019/day7-demo2.txt",
            "65210, src/test/resources/2019/day7-demo3.txt",
            "277328, src/test/resources/2019/day7.txt"})
    void highestSignal(int expected, String fileName) throws IOException, InterruptedException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day7AmplificationCircuit(inputLines).highestSignal());
    }

    @ParameterizedTest
    @CsvSource({"139629729, src/test/resources/2019/day7-demo4.txt",
            "18216, src/test/resources/2019/day7-demo5.txt",
            "11304734, src/test/resources/2019/day7.txt"})
    void highestSignalFeeedbackLoop(int expected, String fileName) throws IOException, InterruptedException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day7AmplificationCircuit(inputLines).highestSignalFeedbackLoop());
    }
}