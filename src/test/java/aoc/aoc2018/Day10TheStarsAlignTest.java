package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2018: Day 10: The Stars Align")
class Day10TheStarsAlignTest {

    @ParameterizedTest
    @CsvSource({"3, HI, src/test/resources/2018/day10-demo1.txt",
            "10007, RECLRNZE, src/test/resources/2018/day10.txt"})
    void problem1(int expected, String message, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day10TheStarsAlign day10TheStarsAlign = new Day10TheStarsAlign();
        assertEquals(expected, day10TheStarsAlign.getMessage(input).getTime());
        //assertEquals(message, day10TheStarsAlign.getMessage(input).getMessage());
    }

}