package aoc.aoc2018;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 3: No Matter How You Slice It")
class Day3NoMatterHowYouSliceItTest {

    @ParameterizedTest
    @CsvSource({"4, src/test/resources/day3-demo1.txt",
            "111630, src/test/resources/day3.txt"})
    void problem1(int expected, String fileName) throws IOException {
        String input = new String(AocFiles.readAllBytes(fileName));
        Day3NoMatterHowYouSliceIt day3NoMatterHowYouSliceIt = new Day3NoMatterHowYouSliceIt();
        assertThat(day3NoMatterHowYouSliceIt.countSlices(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"3, src/test/resources/day3-demo1.txt",
            "724, src/test/resources/day3.txt"})
    void problem2(int expected, String fileName) throws IOException {
        String input = new String(AocFiles.readAllBytes(fileName));
        Day3NoMatterHowYouSliceIt day3NoMatterHowYouSliceIt = new Day3NoMatterHowYouSliceIt();
        assertThat(day3NoMatterHowYouSliceIt.getSingleClaim(input)).isEqualTo(expected);
    }
}