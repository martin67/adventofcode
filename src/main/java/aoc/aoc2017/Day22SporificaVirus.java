package aoc.aoc2017;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day22SporificaVirus {

    final Map<Position, Character> map = new HashMap<>();
    Position virus;
    Direction direction = Direction.Up;

    public Day22SporificaVirus(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (Character c : line.toCharArray()) {
                if (c == '#') {
                    map.put(new Position(x, y), c);
                }
                x++;
            }
            y++;
        }
        virus = new Position(y / 2, y / 2);
    }

    int problem1(int bursts) {
        int infections = 0;
        for (int burst = 0; burst < bursts; burst++) {
            if (map.containsKey(virus)) {
                // infected
                direction = direction.turn(Direction.Right);
                map.remove(virus);
            } else {
                // clean
                direction = direction.turn(Direction.Left);
                map.put(virus, '#');
                infections++;
            }
            virus = virus.adjacent(direction);
        }
        return infections;
    }

    int problem2(int bursts) {
        int infections = 0;
        for (int burst = 0; burst < bursts; burst++) {
            if (map.containsKey(virus)) {
                switch (map.get(virus)) {
                    case 'W' -> {
                        map.put(virus, '#');
                        infections++;
                    }
                    case '#' -> {
                        direction = direction.turn(Direction.Right);
                        map.put(virus, 'F');
                    }
                    case 'F' -> {
                        direction = direction.turn(Direction.Right).turn(Direction.Right);
                        map.remove(virus);
                    }
                }
            } else {
                // clean
                direction = direction.turn(Direction.Left);
                map.put(virus, 'W');
            }
            virus = virus.adjacent(direction);
        }
        return infections;
    }

}