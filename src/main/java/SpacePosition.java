import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class SpacePosition {
    int x;
    int y;
    int z;

    int distance(SpacePosition s) {
        return Math.abs(x - s.x) + Math.abs(y - s.y) + Math.abs(z - s.z);
    }

    Set<SpacePosition> adjacent() {
        Set<SpacePosition> adjacent = new HashSet<>();
        adjacent.add(new SpacePosition(x, y - 1, z)); // up
        adjacent.add(new SpacePosition(x - 1, y, z)); // left
        adjacent.add(new SpacePosition(x + 1, y, z)); // right
        adjacent.add(new SpacePosition(x, y + 1, z)); // down
        adjacent.add(new SpacePosition(x, y, z - 1)); // back
        adjacent.add(new SpacePosition(x, y, z + 1)); // forward
        return adjacent;
    }
}