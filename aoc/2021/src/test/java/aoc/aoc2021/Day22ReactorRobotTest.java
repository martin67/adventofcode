package aoc.aoc2021;

import aoc.aoc2021.Day22ReactorRobot.Cuboid;
import aoc.common.AocFiles;
import aoc.common.SpacePosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2021: Day 22: Reactor Robot")
class Day22ReactorRobotTest {

    @ParameterizedTest
    @CsvSource({"39, day22-demo1.txt",
            "590784, day22-demo2.txt",
            "606484, day22.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day22ReactorRobot(inputLines).problem1b()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2758514936282235, day22-demo3.txt",
            "0, day22.txt"})
    void problem2(String expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day22ReactorRobot(inputLines).problem2()).isEqualTo(expected);
    }

    @Test
    void cubeTests() {
        Cuboid base = new Cuboid(new SpacePosition(0, 0, 0), new SpacePosition(2, 2, 2));
        Cuboid base2 = new Cuboid(new SpacePosition(0, 0, 0), new SpacePosition(2, 2, 2));
        Cuboid cube2 = new Cuboid(new SpacePosition(2, 2, 2), new SpacePosition(4, 4, 4));
        Cuboid cube3 = new Cuboid(new SpacePosition(1, 1, 1), new SpacePosition(4, 4, 4));
        Cuboid big = new Cuboid(new SpacePosition(-1, -1, -1), new SpacePosition(5, 5, 5));
        Cuboid small = new Cuboid(new SpacePosition(0, 0, 0), new SpacePosition(1, 1, 1));
        Cuboid union = new Cuboid(new SpacePosition(1, 1, 1), new SpacePosition(2, 2, 2));
        Cuboid cube4 = new Cuboid(new SpacePosition(0, 0, 1), new SpacePosition(2, 2, 2));

        // Sizes
        assertThat(base.volume()).isEqualTo(27);
        assertThat(union.volume()).isEqualTo(8);
        assertThat(new Cuboid(0, 0, 0, 0, 0, 0).volume()).isEqualTo(1);
        assertThat(new Cuboid(0, 0, 0, 1, 1, 1).volume()).isEqualTo(8);

        // Equality
        assertThat(base).isEqualTo(base2);

        // Intersections
        assertThat(new Cuboid(0, 0, 0, 1, 1, 1).intersect(new Cuboid(2, 0, 0, 3, 1, 1)))
                .isFalse();
        assertThat(Cuboid.complement(new Cuboid(0, 0, 0, 1, 1, 1),
                new Cuboid(1, 1, 1, 2, 2, 2))).hasSize(7);

        assertThat(base.isFullyInsideOf(big)).isTrue();
        assertThat(base.isFullyOutsideOf(small)).isTrue();

        assertThat(base.intersect(cube2)).isFalse();

        assertThat(base.intersect(cube3)).isTrue();
        assertThat(base.isFullyInsideOf(base2)).isTrue();
        assertThat(base.isFullyOutsideOf(base2)).isTrue();

        assertThat(base.intersection(cube3)).isEqualTo(union);
        assertThat(Cuboid.complement(cube3, base)).hasSize(7);
        assertThat(Cuboid.complement(base, cube3)).hasSize(7);

        // base cuboid inside big
        assertThat(Cuboid.complement(big, base)).singleElement().isEqualTo(base);
        assertThat(Cuboid.complement(base, big)).hasSize(8);

        // base cuboid contains small
        assertThat(Cuboid.complement(small, base)).singleElement().isEqualTo(small);
        assertThat(Cuboid.complement(base, small)).singleElement().isEqualTo(small);

        assertThat(Cuboid.complement(cube3, base)).hasSize(8);
        assertThat(Cuboid.complement(base, cube3)).hasSize(1);
        assertThat(Cuboid.complement(cube4, base)).hasSize(2);
        assertThat(Cuboid.complement(base, cube4)).hasSize(1);

        assertThat(Cuboid.complement(
                new Cuboid(11, 11, 11, 13, 13, 13),
                new Cuboid(10, 10, 10, 12, 12, 12))).hasSize(2);
    }
}