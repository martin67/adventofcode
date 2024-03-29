package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 19: Go With The Flow")
class Day19GoWithTheFlowTest {

    @ParameterizedTest
    @CsvSource({
            "6, src/test/resources/day19-demo1.txt",
            "1836, src/test/resources/day19.txt"
    })
    void problem1(int outcome, String fileName) throws IOException {
        Day19GoWithTheFlow day19GoWithTheFlow = new Day19GoWithTheFlow(fileName);
        assertThat(day19GoWithTheFlow.getLeftInRegister()).isEqualTo(outcome);
    }

    @ParameterizedTest
    @CsvSource({
            "18992556, src/test/resources/day19.txt"
    })
    void problem2(int outcome, String fileName) throws IOException {
        Day19GoWithTheFlow day19GoWithTheFlow = new Day19GoWithTheFlow(fileName);
        assertThat(day19GoWithTheFlow.findFactors(10551410)).isEqualTo(outcome);
    }
}