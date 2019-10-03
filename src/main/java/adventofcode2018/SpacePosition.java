package adventofcode2018;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
class SpacePosition {
    long x;
    long y;
    long z;

    long distance(SpacePosition s) {
        return Math.abs(x - s.x) + Math.abs(y - s.y) + Math.abs(z - s.z);
    }

    Set<SpacePosition> adjacent() {
        return adjacent(1);
    }

    Set<SpacePosition> adjacent(int offset) {
        Set<SpacePosition> adjacent = new HashSet<>();
        adjacent.add(new SpacePosition(x, y - offset, z)); // up
        adjacent.add(new SpacePosition(x - offset, y, z)); // left
        adjacent.add(new SpacePosition(x + offset, y, z)); // right
        adjacent.add(new SpacePosition(x, y + offset, z)); // down
        adjacent.add(new SpacePosition(x, y, z - offset)); // back
        adjacent.add(new SpacePosition(x, y, z + offset)); // forward
        return adjacent;
    }
}