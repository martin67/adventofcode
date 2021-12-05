package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2018: Day 3: No Matter How You Slice It")
class Day3NoMatterHowYouSliceItTest {

    @ParameterizedTest
    @CsvSource({"4, src/test/resources/2018/day3-demo1.txt",
            "111630, src/test/resources/2018/day3.txt"})
    void problem1(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day3NoMatterHowYouSliceIt day3NoMatterHowYouSliceIt = new Day3NoMatterHowYouSliceIt();
        assertEquals(expected, day3NoMatterHowYouSliceIt.countSlices(input));
    }

    @ParameterizedTest
    @CsvSource({"3, src/test/resources/2018/day3-demo1.txt",
            "724, src/test/resources/2018/day3.txt"})
    void problem2(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day3NoMatterHowYouSliceIt day3NoMatterHowYouSliceIt = new Day3NoMatterHowYouSliceIt();
        assertEquals(expected, day3NoMatterHowYouSliceIt.getSingleClaim(input));
    }

}