package aoc2019;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14SpaceStoichiometryTest {
    @ParameterizedTest
    @CsvSource({"31, src/test/resources/2019/day14-demo1.txt",
            "165, src/test/resources/2019/day14-demo2.txt",
            "13312, src/test/resources/2019/day14-demo3.txt",
            "180697, src/test/resources/2019/day14-demo4.txt",
            "2210736, src/test/resources/2019/day14-demo5.txt",
            "0, src/test/resources/2019/day14.txt"})
    void minimumAmountOfOre(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day14SpaceStoichiometry(inputLines).minimumAmountOfOre());
    }
}