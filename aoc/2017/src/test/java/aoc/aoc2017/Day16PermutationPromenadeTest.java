package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 16: Permutation Promenade")
class Day16PermutationPromenadeTest {

    @ParameterizedTest
    @CsvSource({"baedc, 5, day16-demo1.txt",
            "glnacbhedpfjkiom, 16, day16.txt"})
    void problem1(String expected, int programSize, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day16PermutationPromenade(inputLines, programSize).danceOrder()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"baedc, 5, day16-demo1.txt",
            "fmpanloehgkdcbji, 16, day16.txt"})
    void problem2(String expected, int programSize, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day16PermutationPromenade(inputLines, programSize).secondDanceOrder()).isEqualTo(expected);
    }
}
