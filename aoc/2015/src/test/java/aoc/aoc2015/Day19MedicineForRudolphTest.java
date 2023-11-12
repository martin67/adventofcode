package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 19: Medicine for Rudolph")
class Day19MedicineForRudolphTest {

    @ParameterizedTest
    @CsvSource({"4, src/test/resources/day19-demo1.txt",
            "7, src/test/resources/day19-demo2.txt",
            "509, src/test/resources/day19.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day19MedicineForRudolph(inputLines).distinctMolecules())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"3, src/test/resources/day19-demo1.txt",
            "6, src/test/resources/day19-demo2.txt",
            "0, src/test/resources/day19.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day19MedicineForRudolph(inputLines).fewestSteps3())
                .isEqualTo(expected);
    }
}