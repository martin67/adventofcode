package aoc.aoc2016;

import aoc.Direction;
import aoc.Position;
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

    private final Graph<Position, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    private final Map<Character, Position> pointsOfInterest = new HashMap<>();

    public Day24AirDuctSpelunking(List<String> inputLines) {
        Set<Position> map = new HashSet<>();
        int y = 0;
        Position pos;

        for (String line : inputLines) {
            int x = 0;
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
                    if (c != '.') {
                        pointsOfInterest.put(c, pos);
                    }
                }
                x++;
            }
            y++;
        }
    }

    int fewestSteps() {
        // reduce to smaller graph
        Graph<Character, DefaultWeightedEdge> smallerGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        DijkstraShortestPath<Position, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);

        for (char start : pointsOfInterest.keySet()) {
            for (char end : pointsOfInterest.keySet()) {
                if (start != end) {
                    if (!smallerGraph.containsVertex(start)) {
                        smallerGraph.addVertex(start);
                    }
                    if (!smallerGraph.containsVertex(end)) {
                        smallerGraph.addVertex(end);
                    }
                    if (!smallerGraph.containsEdge(start, end)) {
                        int length = dijkstraAlg.getPath(pointsOfInterest.get(start), pointsOfInterest.get(end)).getLength();
                        DefaultWeightedEdge e = smallerGraph.addEdge(start, end);
                        smallerGraph.setEdgeWeight(e, length);
                    }
                }
            }
        }

        HeldKarpTSP<Character, DefaultWeightedEdge> tour = new HeldKarpTSP<>();
        int shortestLength = Integer.MAX_VALUE;

        // Change weight between start and end to 0 and compute length => that will give the shortest using TSP
        for (char end : pointsOfInterest.keySet()) {
            if (end != '0') {
                DefaultWeightedEdge e = smallerGraph.getEdge('0', end);
                double weight = smallerGraph.getEdgeWeight(e);
                smallerGraph.setEdgeWeight(e, 0);
                int length = (int) tour.getTour(smallerGraph).getWeight();
                log.info("Length between 0 and {}: {}", end, length);
                if (length < shortestLength) {
                    shortestLength = length;
                }
                smallerGraph.setEdgeWeight(e, weight);
            }
        }

        return shortestLength;
    }
}
