import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import aoc.common.Position;

class PositionTest {

    @Test
    void equality() {
        Position pos1 = new Position(1, 1);
        Position pos2 = new Position(1, 2);
        Position pos3 = new Position(1, 1);

        Assertions.assertThat(pos1).isEqualTo(pos3);
        Assertions.assertThat(pos1).isNotEqualTo(pos2);

        Set<Position> positions = new HashSet<>();
        positions.add(pos1);
        Assertions.assertThat(positions).hasSize(1);
        positions.add(pos2);
        Assertions.assertThat(positions).hasSize(2);
        positions.add(pos3);
        Assertions.assertThat(positions).hasSize(2);

    }
}