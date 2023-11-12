package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 21: Chronal Conversion")
class Day21ChronalConversionTest {

    @Test
    void problem1() throws IOException {
        String fileName = "src/test/resources/day21.txt";
        Day21ChronalConversion day21ChronalConversion = new Day21ChronalConversion(fileName);
        assertThat(day21ChronalConversion.fewestInstructions()).isEqualTo(13443200);
    }

    @Test
    void problem2() throws IOException {
        String fileName = "src/test/resources/day21.txt";
        Day21ChronalConversion day21ChronalConversion = new Day21ChronalConversion(fileName);
        assertThat(day21ChronalConversion.mostInstructions()).isEqualTo(7717135);
    }
}
