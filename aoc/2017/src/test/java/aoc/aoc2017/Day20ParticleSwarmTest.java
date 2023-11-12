package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2017: Day 20: Particle Swarm")
class Day20ParticleSwarmTest {

    @ParameterizedTest
    @CsvSource({"0, src/test/resources/day20-demo1.txt",
            "170, src/test/resources/day20.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day20ParticleSwarm(Files.readAllLines(Paths.get(fileName))).problem1());
    }

    @ParameterizedTest
    @CsvSource({"1, src/test/resources/day20-demo2.txt",
            "571, src/test/resources/day20.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day20ParticleSwarm(Files.readAllLines(Paths.get(fileName))).problem2());
    }

}