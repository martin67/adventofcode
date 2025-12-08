import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class Day4PrintingDepartment {

    Set<Position> map = new HashSet<>();

    Day4PrintingDepartment(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == '@') {
                    map.add(new Position(x, y));
                }
                x++;
            }
            y++;
        }
    }

    int problem1() {
        int result = 0;
        for (var position : map) {
            long intersection = position.allAdjacentIncludingDiagonal().stream()
                    .filter(map::contains)
                    .count();
            if (intersection < 4) {
                result++;
            }
        }
        return result;
    }

    int problem2() {
        Set<Position> removable = new HashSet<>();
        int originalRolls = map.size();

        do {
            removable.clear();
            for (var position : map) {
                long intersection = position.allAdjacentIncludingDiagonal().stream()
                        .filter(map::contains)
                        .count();
                if (intersection < 4) {
                    removable.add(position);
                }
            }
            map.removeAll(removable);
        } while (!removable.isEmpty());

        return originalRolls - map.size();
    }
}
