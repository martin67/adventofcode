package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 20: Jurassic Jigsaw")
class Day20JurassicJigsawTest {

    @ParameterizedTest
    @CsvSource({"20899048083289, day20-demo1.txt",
            "21599955909991, day20.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day20JurassicJigsaw(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"273, day20-demo1.txt",
            "2495, day20.txt"})
    void problem2(long expected, String fileName) throws Exception {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day20JurassicJigsaw(inputLines).problem2()).isEqualTo(expected);
    }
}