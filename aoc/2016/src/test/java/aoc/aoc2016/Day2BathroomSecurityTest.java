package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 2: Bathroom Security")
class Day2BathroomSecurityTest {

    @ParameterizedTest
    @CsvSource({"1985, src/test/resources/day2-demo1.txt",
            "61529, src/test/resources/day2.txt"})
    void problem1(String expected, String fileName) throws IOException {
        assertThat(new Day2BathroomSecurity(fileName).bathroomCode()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"5DB3, src/test/resources/day2-demo1.txt",
            "C2C28, src/test/resources/day2.txt"})
    void problem2(String expected, String fileName) throws IOException {
        assertThat(new Day2BathroomSecurity(fileName).secondBathroomCode()).isEqualTo(expected);
    }
}