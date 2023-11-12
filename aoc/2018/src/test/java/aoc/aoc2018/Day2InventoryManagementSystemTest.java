package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 2: Inventory Management System")
class Day2InventoryManagementSystemTest {

    @ParameterizedTest
    @CsvSource({
            "abcdef, 0, 0",
            "bababc, 1, 1",
            "abbcde, 1, 0",
            "abcccd, 0, 1",
            "aabcdd, 1, 0",
            "abcdee, 1, 0",
            "ababab, 0, 1"
    })
    void computeRowChecksum(String input, Integer twos, Integer threes) {
        Day2InventoryManagementSystem day2InventoryManagementSystem = new Day2InventoryManagementSystem();
        assertThat(day2InventoryManagementSystem.computeRowChecksum(input).numberOfTwos).isEqualTo(twos);
        assertThat(day2InventoryManagementSystem.computeRowChecksum(input).numberOfThrees).isEqualTo(threes);
    }

    @ParameterizedTest
    @CsvSource({
            "abcdef bababc abbcde abcccd aabcdd abcdee ababab, 12"
    })
    void computeChecksum(String input, Integer checksum) {
        Day2InventoryManagementSystem day2InventoryManagementSystem = new Day2InventoryManagementSystem();
        assertThat(day2InventoryManagementSystem.computeChecksum(input)).isEqualTo(checksum);
    }

    @Test
    void problem1() throws IOException {
        Day2InventoryManagementSystem day2InventoryManagementSystem = new Day2InventoryManagementSystem();
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/day2.txt"))));
        assertThat(day2InventoryManagementSystem.computeChecksum(input)).isEqualTo(8296);
    }

    @ParameterizedTest
    @CsvSource({
            "abcde fghij klmno pqrst fguij axcye wvxyz, fgij"
    })
    void findBoxes(String input, String expected) {
        Day2InventoryManagementSystem day2InventoryManagementSystem = new Day2InventoryManagementSystem();
        assertThat(day2InventoryManagementSystem.findBoxes(input)).isEqualTo(expected);
    }

    @Test
    void problem2() throws IOException {
        Day2InventoryManagementSystem day2InventoryManagementSystem = new Day2InventoryManagementSystem();
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/day2.txt"))));
        assertThat(day2InventoryManagementSystem.findBoxes(input)).isEqualTo("pazvmqbftrbeosiecxlghkwud");
    }
}