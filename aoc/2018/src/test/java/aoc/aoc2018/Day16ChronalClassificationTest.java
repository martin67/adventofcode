package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 16: Chronal Classification")
class Day16ChronalClassificationTest {

    @ParameterizedTest
    @CsvSource({"1, src/test/resources/day16-demo1.txt",
            "493, src/test/resources/day16.txt"})
    void problem1(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day16ChronalClassification day16ChronalClassification = new Day16ChronalClassification(input);
        assertThat(day16ChronalClassification.threeOrMoreOpCodes()).isEqualTo(expected);
    }

    @Test
    void problem2() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/day16.txt"))));
        Day16ChronalClassification day16ChronalClassification = new Day16ChronalClassification(input);
        assertThat(day16ChronalClassification.remainingRegister()).isEqualTo(445);
    }
}