package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 25: Full of Hot Air")
class Day25FullOfHotAirTest {

    @ParameterizedTest
    @CsvSource({"2=-1=0, src/test/resources/2022/day25-demo1.txt",
            "20-1-0=-2=-2220=0011, src/test/resources/2022/day25.txt"})
    void problem1(String expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day25FullOfHotAir(inputLines).problem1())
                .isEqualTo(expected);
    }

}