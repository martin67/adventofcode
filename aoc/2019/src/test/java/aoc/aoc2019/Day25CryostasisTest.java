package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 25: Cryostasis")
class Day25CryostasisTest {

    @Test
    void problem1() throws IOException, InterruptedException, ExecutionException {
        var inputLines = AocFiles.readAllLines("day25.txt");
        assertThat(new Day25Cryostasis(inputLines).getPassword()).isEqualTo("password");
    }
}