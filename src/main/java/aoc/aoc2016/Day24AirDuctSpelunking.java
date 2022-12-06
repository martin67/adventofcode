package aoc.aoc2016;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

@Slf4j
public class Day24AirDuctSpelunking {

    private final Graph<Character, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    private final Map<Character, Position> pointsOfInterest = new HashMap<>();

    public Day24AirDuctSpelunking(List<String> inputLines) {
        Graph<Position, DefaultEdge> initialGraph = new SimpleGraph<>(DefaultEdge.class);
        DijkstraShortestPath<Position, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(initialGraph);
        Set<Position> map = new HashSet<>();
        int y = 0;
        Position pos;

        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c != '#') {
                    pos = new Position(x, y);
                    map.add(pos);
                    initialGraph.addVertex(pos);
                    Position left = pos.adjacent(Direction.Left);
                    if (map.contains(left)) {
                        initialGraph.addEdge(pos, left);
                    }
                    Position up = pos.adjacent(Direction.Up);
                    if (map.contains(up)) {
                        initialGraph.addEdge(pos, up);
                    }
                    if (c != '.') {
                        pointsOfInterest.put(c, pos);
                    }
                }
                x++;
            }
            y++;
        }

        // reduce to smaller graph
        for (char start : pointsOfInterest.keySet()) {
            for (char end : pointsOfInterest.keySet()) {
                if (start != end) {
                    if (!graph.containsVertex(start)) {
                        graph.addVertex(start);
                    }
                    if (!graph.containsVertex(end)) {
                        graph.addVertex(end);
                    }
                    if (!graph.containsEdge(start, end)) {
                        int length = dijkstraAlg.getPath(pointsOfInterest.get(start), pointsOfInterest.get(end)).getLength();
                        DefaultWeightedEdge e = graph.addEdge(start, end);
                        graph.setEdgeWeight(e, length);
                    }
                }
            }
        }
    }

    int fewestSteps() {

        HeldKarpTSP<Character, DefaultWeightedEdge> tour = new HeldKarpTSP<>();
        int shortestLength = Integer.MAX_VALUE;

        // Change weight between start and end to 0 and compute length => that will give the shortest using TSP
        for (char end : pointsOfInterest.keySet()) {
            if (end != '0') {
                DefaultWeightedEdge e = graph.getEdge('0', end);
                double weight = graph.getEdgeWeight(e);
                graph.setEdgeWeight(e, 0);
                int length = (int) tour.getTour(graph).getWeight();
                log.info("Length between 0 and {}: {}", end, length);
                if (length < shortestLength) {
                    shortestLength = length;
                }
                graph.setEdgeWeight(e, weight);
            }
        }

        return shortestLength;
    }

    int fewestStepsAndReturn() {
        HeldKarpTSP<Character, DefaultWeightedEdge> tour = new HeldKarpTSP<>();
        int length = (int) tour.getTour(graph).getWeight();
        log.info("TSP length: {}", length);
        return length;
    }
}
