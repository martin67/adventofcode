package aoc.aoc2022;

import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day12HillClimbingAlgorithm {

    Map<Position, Integer> map = new HashMap<>();
    Graph<Position, DefaultEdge> graph = new SimpleDirectedGraph<>(DefaultEdge.class);
    DijkstraShortestPath<Position, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
    Position start;
    Position end;

    public Day12HillClimbingAlgorithm(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (Character c : line.toCharArray()) {
                Position p = new Position(x, y);
                int height = switch (c) {
                    case 'S' -> {
                        start = p;
                        yield 0;
                    }
                    case 'E' -> {
                        end = p;
                        yield 25;
                    }
                    default -> c - 97;
                };
                map.put(p, height);
                x++;
            }
            y++;
        }

        // create graph
        map.forEach((k, v) -> graph.addVertex(k));
        for (Map.Entry<Position, Integer> entry : map.entrySet()) {
            int sourceHeight = entry.getValue();
            for (Position p : entry.getKey().allAdjacent()) {
                if (map.containsKey(p)) {
                    int destinationHeight = map.get(p);
                    if (destinationHeight - sourceHeight <= 1) {
                        graph.addEdge(entry.getKey(), p);
                    }
                }
            }
        }
    }

    int problem1() {
        return dijkstraAlg.getPath(start, end).getLength();
    }

    int problem2() {
        List<Position> startPositions = map.entrySet().stream()
                .filter(entry -> entry.getValue() == 0)
                .map(Map.Entry::getKey)
                .toList();
        int shortestLength = Integer.MAX_VALUE;
        for (Position p : startPositions) {
            GraphPath<Position, DefaultEdge> path = dijkstraAlg.getPath(p, end);
            if (path != null) {
                shortestLength = Math.min(path.getLength(), shortestLength);
            }
        }
        return shortestLength;
    }

}
