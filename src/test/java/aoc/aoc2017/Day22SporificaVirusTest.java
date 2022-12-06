package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 22: Sporifica Virus")
class Day22SporificaVirusTest {

    @ParameterizedTest
    @CsvSource({"5, 7, src/test/resources/2017/day22-demo1.txt",
            "41, 70, src/test/resources/2017/day22-demo1.txt",
            "5587, 10000, src/test/resources/2017/day22-demo1.txt",
            "5348, 10000, src/test/resources/2017/day22.txt"})
    void problem1(int expected, int bursts, String fileName) throws IOException {
        assertThat(new Day22SporificaVirus(Files.readAllLines(Paths.get(fileName))).problem1(bursts))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"26, 100, src/test/resources/2017/day22-demo1.txt",
            "2511944, 10000000, src/test/resources/2017/day22-demo1.txt",
            "2512225, 10000000, src/test/resources/2017/day22.txt"})
    void problem2(int expected, int bursts, String fileName) throws IOException {
        assertThat(new Day22SporificaVirus(Files.readAllLines(Paths.get(fileName))).problem2(bursts))
                .isEqualTo(expected);
    }

}