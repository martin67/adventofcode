package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 14: Regolith Reservoir")
class Day14RegolithReservoirTest {

    @ParameterizedTest
    @CsvSource({"24, src/test/resources/2022/day14-demo1.txt",
            "1068, src/test/resources/2022/day14.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day14RegolithReservoir(inputLines).problem1(false))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"93, src/test/resources/2022/day14-demo1.txt",
            "27936, src/test/resources/2022/day14.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day14RegolithReservoir(inputLines).problem2())
                .isEqualTo(expected);
    }

}