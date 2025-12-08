import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2025: Day 4: Printing Department")
class Day4PrintingDepartmentTest {

    @ParameterizedTest
    @CsvSource({"0, day4-demo1.txt",
            "0, day4.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day4PrintingDepartment(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"0, day4-demo1.txt",
            "0, day4.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day4PrintingDepartment(inputLines).problem2()).isEqualTo(expected);
    }
}