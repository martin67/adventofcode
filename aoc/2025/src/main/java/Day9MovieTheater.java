import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day9MovieTheater {
    Set<Position> map = new HashSet<>();

    public Day9MovieTheater(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s = line.split(",");
            map.add(new Position(Integer.parseInt(s[0]), Integer.parseInt(s[1])));
        }
    }

    public long problem1() {
        long maxSize = 0;
        for (var start : map) {
            for (var end : map) {
                if (!start.equals(end)) {
                    long size = (Math.abs(start.getX() - end.getX()) + 1L) * (Math.abs(start.getY() - end.getY()) + 1L);
                    if (size > maxSize) {
                        maxSize = size;
                    }
                }
            }
        }
        return maxSize;
    }

    public int problem2() {
        return 0;
    }
}
