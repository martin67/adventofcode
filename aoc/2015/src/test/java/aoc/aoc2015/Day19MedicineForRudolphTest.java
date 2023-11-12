package aoc.aoc2015;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 19: Medicine for Rudolph")
class Day19MedicineForRudolphTest {

    @ParameterizedTest
    @CsvSource({"4, day19-demo1.txt",
            "7, day19-demo2.txt",
            "509, day19.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day19MedicineForRudolph(inputLines).distinctMolecules()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"3, day19-demo1.txt",
            "6, day19-demo2.txt",
            "0, day19.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day19MedicineForRudolph(inputLines).fewestSteps3()).isEqualTo(expected);
    }
}