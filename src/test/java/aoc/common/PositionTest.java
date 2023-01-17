package aoc.common;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class PositionTest {

    @Test
    void equality() {
        Position pos1 = new Position(1, 1);
        Position pos2 = new Position(1, 2);
        Position pos3 = new Position(1, 1);

        assertThat(pos1).isEqualTo(pos3);
        assertThat(pos1).isNotEqualTo(pos2);

        Set<Position> positions = new HashSet<>();
        positions.add(pos1);
        assertThat(positions).hasSize(1);
        positions.add(pos2);
        assertThat(positions).hasSize(2);
        positions.add(pos3);
        assertThat(positions).hasSize(2);

    }
}