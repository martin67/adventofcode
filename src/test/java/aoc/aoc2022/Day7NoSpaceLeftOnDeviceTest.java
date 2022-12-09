package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 7: No Space Left On Device")
class Day7NoSpaceLeftOnDeviceTest {

    @ParameterizedTest
    @CsvSource({"95437, src/test/resources/2022/day7-demo1.txt",
            "1182909, src/test/resources/2022/day7.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day7NoSpaceLeftOnDevice(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"24933642, src/test/resources/2022/day7-demo1.txt",
            "2832508, src/test/resources/2022/day7.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day7NoSpaceLeftOnDevice(inputLines).problem2())
                .isEqualTo(expected);
    }

}