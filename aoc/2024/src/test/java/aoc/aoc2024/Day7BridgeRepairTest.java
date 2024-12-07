package aoc.aoc2024;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2024: Day 7: Bridge Repair")
class Day7BridgeRepairTest {

    @ParameterizedTest
    @CsvSource({"3749, day7-demo1.txt",
            "5512534574980, day7.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day7BridgeRepair(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"11387, day7-demo1.txt",
            "328790210468594, day7.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day7BridgeRepair(inputLines).problem2()).isEqualTo(expected);
    }
}