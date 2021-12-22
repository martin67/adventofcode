package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 22: Reactor Robot")
class Day22ReactorRobotTest {

    @ParameterizedTest
    @CsvSource({"39, src/test/resources/2021/day22-demo1.txt",
            "590784, src/test/resources/2021/day22-demo2.txt",
            "606484, src/test/resources/2021/day22.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day22ReactorRobot(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"2758514936282235, src/test/resources/2021/day22-demo3.txt",
            "0, src/test/resources/2021/day22.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day22ReactorRobot(inputLines).problem2());
    }

}