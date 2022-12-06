package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 6: Tuning Trouble")
class Day6TuningTroubleTest {

    @ParameterizedTest
    @CsvSource({"7, src/test/resources/2022/day6-demo1.txt",
            "5, src/test/resources/2022/day6-demo2.txt",
            "6, src/test/resources/2022/day6-demo3.txt",
            "10, src/test/resources/2022/day6-demo4.txt",
            "11, src/test/resources/2022/day6-demo5.txt",
            "1531, src/test/resources/2022/day6.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day6TuningTrouble(inputLines).problem(4))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"19, src/test/resources/2022/day6-demo1.txt",
            "23, src/test/resources/2022/day6-demo2.txt",
            "23, src/test/resources/2022/day6-demo3.txt",
            "29, src/test/resources/2022/day6-demo4.txt",
            "26, src/test/resources/2022/day6-demo5.txt",
            "2518, src/test/resources/2022/day6.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day6TuningTrouble(inputLines).problem(14))
                .isEqualTo(expected);
    }

}