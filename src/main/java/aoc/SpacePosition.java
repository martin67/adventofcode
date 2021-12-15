package aoc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class SpacePosition {
    private int x;
    private int y;
    private int z;

    public int distance(SpacePosition s) {
        return Math.abs(x - s.x) + Math.abs(y - s.y) + Math.abs(z - s.z);
    }

    public Set<SpacePosition> adjacent() {
        return adjacent(1);
    }

    public Set<SpacePosition> adjacent(int offset) {
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
