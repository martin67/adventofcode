package aoc.aoc2016;

import aoc.Direction;
import aoc.Position;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class Day17TwoStepsForward {
    String passcode;
    Set<Position> rooms = new HashSet<>();
    String shortestPath;
    String longestPath;
    Position start = new Position(0, 0);
    Position end = new Position(3, 3);

    public Day17TwoStepsForward(String passcode) {
        this.passcode = passcode;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                rooms.add(new Position(x, y));
            }
        }
    }

    String shortestPath() {
        pathRunner(start, "");
        return shortestPath;
    }

    int longestPath() {
        pathRunner(start, "");
        return longestPath.length();
    }

    void pathRunner(Position position, String path) {

        if (position.equals(end)) {
            log.debug("Found target {}, path {}", position, path);
            if (shortestPath == null || path.length() < shortestPath.length()) {
                shortestPath = path;
            }
            if (longestPath == null || path.length() > longestPath.length()) {
                longestPath = path;
            }
        } else {
            String hash = Hashing.md5().hashString(passcode + path, Charsets.UTF_8).toString();
            Set<Direction> possibleDirections = openDoors(hash);

            for (Direction direction : possibleDirections) {
                if (rooms.contains(position.adjacent(direction))) {
                    log.debug("Found direction {} from {}", direction, position);
                    pathRunner(position.adjacent(direction), path + direction.shortName());
                }
            }
        }
    }

    Set<Direction> openDoors(String hash) {
        Set<Direction> result = new HashSet<>();
        if (hash.charAt(0) > 'a') {
            result.add(Direction.Up);
        }
        if (hash.charAt(1) > 'a') {
            result.add(Direction.Down);
        }
        if (hash.charAt(2) > 'a') {
            result.add(Direction.Left);
        }
        if (hash.charAt(3) > 'a') {
            result.add(Direction.Right);
        }
        return result;
    }

}
