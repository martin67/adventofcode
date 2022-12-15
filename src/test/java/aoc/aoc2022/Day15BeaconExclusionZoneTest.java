package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 15: Beacon Exclusion Zone")
class Day15BeaconExclusionZoneTest {

    @ParameterizedTest
    @CsvSource({"25, 9, src/test/resources/2022/day15-demo1.txt",
            "26, 10, src/test/resources/2022/day15-demo1.txt",
            "28, 11, src/test/resources/2022/day15-demo1.txt",
            "5240818, 2000000, src/test/resources/2022/day15.txt"})
    void problem1(int expected, int row, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day15BeaconExclusionZone(inputLines).problem1(row))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"56000011, 20, src/test/resources/2022/day15-demo1.txt",
            "13213086906101, 4000000, src/test/resources/2022/day15.txt"})
    void problem2(long expected, int searchArea, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day15BeaconExclusionZone(inputLines).problem2(searchArea))
                .isEqualTo(expected);
    }

    //1767503605 too low
}