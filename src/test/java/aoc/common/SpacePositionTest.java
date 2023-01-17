package aoc.common;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class SpacePositionTest {

    @Test
    void equality() {
        SpacePosition pos1 = new SpacePosition(1, 1, 1);
        SpacePosition pos2 = new SpacePosition(1, 1, 2);
        SpacePosition pos3 = new SpacePosition(1, 1, 1);

        assertThat(pos1).isEqualTo(pos3);
        assertThat(pos1).isNotEqualTo(pos2);

        Set<SpacePosition> positions = new HashSet<>();
        positions.add(pos1);
        assertThat(positions).hasSize(1);
        positions.add(pos2);
        assertThat(positions).hasSize(2);
        positions.add(pos3);
        assertThat(positions).hasSize(2);

    }
}