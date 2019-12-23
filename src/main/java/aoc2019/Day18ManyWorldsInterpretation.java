package aoc2019;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

public class Day18ManyWorldsInterpretation {

    Set<Position> map = new HashSet<>();
    Map<Character, Position> keys = new HashMap<>();
    Map<Character, Position> doors = new HashMap<>();
    Position start;
    private final Graph<Position, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

    public Day18ManyWorldsInterpretation(List<String> inputLines) {
        int x;
        int y = 0;
        Position pos;
        for (String line : inputLines) {
            x = 0;
            for (char c : line.toCharArray()) {
                if (c != '#') {
                    pos = new Position(x, y);
                    map.add(pos);
                    graph.addVertex(pos);
                    Position left = pos.adjacent(Direction.Left);
                    if (map.contains(left)) {
                        graph.addEdge(pos, left);
                    }
                    Position up = pos.adjacent(Direction.Up);
                    if (map.contains(up)) {
                        graph.addEdge(pos, up);
                    }
                    if (c == '@') {
                        start = pos;
                    } else if (c != '.') {
                        if (Character.isUpperCase(c)) {
                            doors.put(c, pos);
                        } else {
                            keys.put(Character.toUpperCase(c), pos);
                        }
                    }
                }
                x++;
            }
            y++;
        }

    }

    int shortestPath() {
        //getAllReachableKeys();
        return 0;
    }
}
