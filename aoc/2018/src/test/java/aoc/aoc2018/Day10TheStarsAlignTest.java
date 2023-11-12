package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 10: The Stars Align")
class Day10TheStarsAlignTest {

    @ParameterizedTest
    @CsvSource({"3, HI, src/test/resources/day10-demo1.txt",
            "10007, RECLRNZE, src/test/resources/day10.txt"})
    void problem1(int expected, String message, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day10TheStarsAlign day10TheStarsAlign = new Day10TheStarsAlign();
        assertThat(day10TheStarsAlign.getMessage(input).getTime()).isEqualTo(expected);
        //assertEquals(message, day10TheStarsAlign.getMessage(input).getMessage());
    }
}