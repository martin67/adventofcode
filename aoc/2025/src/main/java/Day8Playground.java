import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aoc.common.SpacePosition;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day8Playground {
    Set<SpacePosition> boxes = new HashSet<>();

    public Day8Playground(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s = line.split(",");
            boxes.add(new SpacePosition(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])));
        }
    }

    public int problem1() {
        Map<Set<SpacePosition>, Integer> distanceMap = new HashMap<>();
        for(var i : boxes) {
            for (var j : boxes) {
                if (!i.equals(j)) {
                        distanceMap.put(Set.of(i,j), i.distance(j));
                }
            }
        }
        for (int i = 0; i < 10; i++) {

        }
        return 0;
    }

    public int problem2() {
        return 0;
    }
}
