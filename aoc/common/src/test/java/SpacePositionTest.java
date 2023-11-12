import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import aoc.common.SpacePosition;

class SpacePositionTest {

    @Test
    void equality() {
        SpacePosition pos1 = new SpacePosition(1, 1, 1);
        SpacePosition pos2 = new SpacePosition(1, 1, 2);
        SpacePosition pos3 = new SpacePosition(1, 1, 1);

        Assertions.assertThat(pos1).isEqualTo(pos3);
        Assertions.assertThat(pos1).isNotEqualTo(pos2);

        Set<SpacePosition> positions = new HashSet<>();
        positions.add(pos1);
        Assertions.assertThat(positions).hasSize(1);
        positions.add(pos2);
        Assertions.assertThat(positions).hasSize(2);
        positions.add(pos3);
        Assertions.assertThat(positions).hasSize(2);

    }
}