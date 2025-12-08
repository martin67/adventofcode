import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

@DisplayName("2024: Day 6: Trash Compactor")
class Day6TrashCompactorTest {

    @ParameterizedTest
    @CsvSource({"4277556, day6-demo1.txt",
            "4805473544166, day6.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        Assertions.assertThat(new Day6TrashCompactor(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"3263827, day6-demo1.txt",
            "0, day6.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        Assertions.assertThat(new Day6TrashCompactor(inputLines).problem2()).isEqualTo(expected);
    }
}