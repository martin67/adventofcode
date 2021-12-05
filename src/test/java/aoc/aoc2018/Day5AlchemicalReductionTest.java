package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2018: Day 5: Alchemical Reduction")
class Day5AlchemicalReductionTest {

    @ParameterizedTest
    @CsvSource({"10, src/test/resources/2018/day5-demo1.txt",
            "9526, src/test/resources/2018/day5.txt"})
    void problem1(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day5AlchemicalReduction day5AlchemicalReduction = new Day5AlchemicalReduction();
        assertEquals(expected, day5AlchemicalReduction.getPolymerLength(input));
    }

    @ParameterizedTest
    @CsvSource({"4, src/test/resources/2018/day5-demo1.txt",
            "6694, src/test/resources/2018/day5.txt"})
    void problem2(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day5AlchemicalReduction day5AlchemicalReduction = new Day5AlchemicalReduction();
        assertEquals(expected, day5AlchemicalReduction.findShortestPolymer(input));
    }

}