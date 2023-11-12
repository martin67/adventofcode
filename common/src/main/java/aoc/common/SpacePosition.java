package aoc.common;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class SpacePosition {
    private int x;
    private int y;
    private int z;

    public SpacePosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public SpacePosition(String x, String y, String z) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.z = Integer.parseInt(z);
    }

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

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
