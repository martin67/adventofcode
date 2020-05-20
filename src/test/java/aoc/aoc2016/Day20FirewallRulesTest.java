package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20FirewallRulesTest {

    @ParameterizedTest
    @CsvSource({"3, src/test/resources/2016/day20-demo1.txt",
            "22887907, src/test/resources/2016/day20.txt"})
    void lowestValuedIp(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day20FirewallRules(inputLines).lowestValuedIp());
    }
}