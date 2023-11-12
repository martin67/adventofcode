package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2016: Day 20: Firewall Rules")
class Day20FirewallRulesTest {

    @ParameterizedTest
    @CsvSource({"3, src/test/resources/day20-demo1.txt",
            "22887907, src/test/resources/day20.txt"})
    void problem1(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day20FirewallRules(inputLines).lowestValuedIp());
    }

    @ParameterizedTest
    @CsvSource({"2, 0, 9, src/test/resources/day20-demo1.txt",
            "109, 0, 4294967295, src/test/resources/day20.txt"})
    void problem2(long expected, long min, long max, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day20FirewallRules(inputLines).ipsAllowed(min, max));
    }

}