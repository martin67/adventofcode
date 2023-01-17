package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 13: Distress Signal")
class Day13DistressSignalTest {

    @ParameterizedTest
    @CsvSource({"13, src/test/resources/2022/day13-demo1.txt",
            "7763, src/test/resources/2022/day13.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day13DistressSignal(inputLines).problem1())
                .isEqualTo(expected);
    }
// 6240, 6302, 6116 too high
// 5704, 5754, 5804


    @ParameterizedTest
    @CsvSource({"70, src/test/resources/2022/day13-demo1.txt",
            "2569, src/test/resources/2022/day13.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day13DistressSignal(inputLines).problem2())
                .isEqualTo(expected);
    }

}