package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(twos, day2InventoryManagementSystem.computeRowChecksum(input).numberOfTwos);
        assertEquals(threes, day2InventoryManagementSystem.computeRowChecksum(input).numberOfThrees);
    }

    @ParameterizedTest
    @CsvSource({
            "abcdef bababc abbcde abcccd aabcdd abcdee ababab, 12"
    })
    void computeChecksum(String input, Integer checksum) {
        Day2InventoryManagementSystem day2InventoryManagementSystem = new Day2InventoryManagementSystem();
        assertEquals(checksum, day2InventoryManagementSystem.computeChecksum(input));
    }

    @Test
    void problem1() throws IOException {
        Day2InventoryManagementSystem day2InventoryManagementSystem = new Day2InventoryManagementSystem();
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/2018/day2.txt"))));
        assertEquals(8296, day2InventoryManagementSystem.computeChecksum(input));
    }

    @ParameterizedTest
    @CsvSource({
            "abcde fghij klmno pqrst fguij axcye wvxyz, fgij"
    })
    void findBoxes(String input, String expected) {
        Day2InventoryManagementSystem day2InventoryManagementSystem = new Day2InventoryManagementSystem();
        assertEquals(expected, day2InventoryManagementSystem.findBoxes(input));
    }

    @Test
    void problem2() throws IOException {
        Day2InventoryManagementSystem day2InventoryManagementSystem = new Day2InventoryManagementSystem();
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/2018/day2.txt"))));
        assertEquals("pazvmqbftrbeosiecxlghkwud", day2InventoryManagementSystem.findBoxes(input));
    }

}