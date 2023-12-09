package aoc.aoc2023;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("2023: Day 9: Mirage Maintenance")
class Day9MirageMaintenanceTest {

    @ParameterizedTest
    @CsvSource({"114, day9-demo1.txt",
            "1974232246, day9.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day9MirageMaintenance(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2, day9-demo1.txt",
            "928, day9.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day9MirageMaintenance(inputLines).problem2()).isEqualTo(expected);
    }
}