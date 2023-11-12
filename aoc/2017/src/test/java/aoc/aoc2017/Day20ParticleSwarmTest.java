package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 20: Particle Swarm")
class Day20ParticleSwarmTest {

    @ParameterizedTest
    @CsvSource({"0, day20-demo1.txt",
            "170, day20.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day20ParticleSwarm(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1, day20-demo2.txt",
            "571, day20.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day20ParticleSwarm(inputLines).problem2()).isEqualTo(expected);
    }
}