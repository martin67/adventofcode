package aoc.aoc2016;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 20: Firewall Rules")
class Day20FirewallRulesTest {

    @ParameterizedTest
    @CsvSource({"3, day20-demo1.txt",
            "22887907, day20.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day20FirewallRules(inputLines).lowestValuedIp()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2, 0, 9, day20-demo1.txt",
            "109, 0, 4294967295, day20.txt"})
    void problem2(long expected, long min, long max, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day20FirewallRules(inputLines).ipsAllowed(min, max)).isEqualTo(expected);
    }
}