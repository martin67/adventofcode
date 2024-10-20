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

    final Map<Position, Integer> map = new HashMap<>();
    final Graph<Position, DefaultEdge> graph = new SimpleDirectedGraph<>(DefaultEdge.class);
    final DijkstraShortestPath<Position, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
    Position start;
    Position end;

    Day12HillClimbingAlgorithm(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (Character c : line.toCharArray()) {
                var position = new Position(x, y);
                int height = switch (c) {
                    case 'S' -> {
                        start = position;
                        yield 0;
                    }
                    case 'E' -> {
                        end = position;
                        yield 25;
                    }
                    default -> c - 97;
                };
                map.put(position, height);
                x++;
            }
            y++;
        }

        // create graph
        map.forEach((k, v) -> graph.addVertex(k));
        for (Map.Entry<Position, Integer> entry : map.entrySet()) {
            int sourceHeight = entry.getValue();
            for (var position : entry.getKey().allAdjacent()) {
                if (map.containsKey(position)) {
                    int destinationHeight = map.get(position);
                    if (destinationHeight - sourceHeight <= 1) {
                        graph.addEdge(entry.getKey(), position);
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
        for (var position : startPositions) {
            GraphPath<Position, DefaultEdge> path = dijkstraAlg.getPath(position, end);
            if (path != null) {
                shortestLength = Math.min(path.getLength(), shortestLength);
            }
        }
        return shortestLength;
    }

}
