package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2018: Day 12: Subterranean Sustainability")
class Day12SubterraneanSustainabilityTest {

    @ParameterizedTest
    @CsvSource({"325, 20, src/test/resources/2018/day12-demo1.txt",
            "3061, 20, src/test/resources/2018/day12.txt"})
    void problem1(int expected, int generations, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day12SubterraneanSustainability(inputLines).problem1(generations))
                .isEqualTo(expected);
    }

    @Test
    void problem2() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/2018/day12.txt"))));
        Day12SubterraneanSustainability day12SubterraneanSustainability = new Day12SubterraneanSustainability(input, 205);
        assertEquals("4049999998575", day12SubterraneanSustainability.ComputeBigPlantSum(new BigInteger("50000000000")));
    }

}