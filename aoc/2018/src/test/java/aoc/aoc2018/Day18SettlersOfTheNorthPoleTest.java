package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 18: Settlers of The North Pole")
class Day18SettlersOfTheNorthPoleTest {

    @ParameterizedTest
    @CsvSource({
            "1147, src/test/resources/day18-demo1.txt",
            "588436, src/test/resources/day18.txt"})
    void problem1(int outcome, String fileName) throws IOException {
        Day18SettlersOfTheNorthPole day18SettlersOfTheNorthPole = new Day18SettlersOfTheNorthPole(fileName);
        assertThat(day18SettlersOfTheNorthPole.computeResourceValue()).isEqualTo(outcome);
    }

    @Test
    void problem2() throws IOException {
        String fileName = "src/test/resources/day18.txt";
        Day18SettlersOfTheNorthPole day18SettlersOfTheNorthPole = new Day18SettlersOfTheNorthPole(fileName);
        assertThat(day18SettlersOfTheNorthPole.totalResourceValue()).isEqualTo(195290);
    }
}