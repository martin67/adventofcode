package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 23: Coprocessor Conflagration")
class Day23CoprocessorConflagrationTest {

    @ParameterizedTest
    @CsvSource({"9409, src/test/resources/day23.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertThat(new Day23CoprocessorConflagration(Files.readAllLines(Paths.get(fileName))).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1001, src/test/resources/day23.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertThat(new Day23CoprocessorConflagration(Files.readAllLines(Paths.get(fileName))).problem2())
                .isEqualTo(expected);
    }

    @Test
    void manualRun() {
        int b = 109900;
        int c = 126900;
        int h = 0;

        while (true) {
            int f = 1;
            outerloop:
            for (int d = 2; d < b; d++) {
                for (int e = 2; e < b; e++) {
                    if (d * e == b) {
                        f = 0;
                        break outerloop;
                    } else if (d * e > b) {
                        break;
                    }
                }

            }
            if (f == 0) {
                h = h + 1;
            }
            if (b == c) {
                System.out.println("h: " + h);
                return;
            }
            b = b + 17;
        }
    }
}