package aoc.aoc2016;

import aoc.Direction;
import aoc.Position;
import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.HashSet;
import java.util.Set;

public class Day13AMazeOfTwistyLittleCubicles {
    private final Graph<Position, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
    final int favoriteNumber;

    public Day13AMazeOfTwistyLittleCubicles(int favoriteNumber) {
        this.favoriteNumber = favoriteNumber;
    }

    int fewestNumberOfSteps(Position endPosition) {

        // Make map
        Set<Position> map = new HashSet<>();

        for (int y = 0; y < endPosition.getY() + 10; y++) {
            for (int x = 0; x < endPosition.getX() + 10; x++) {
                Position p = new Position(x, y);
                if (isOpenSpace(p)) {
                    map.add(p);
                    graph.addVertex(p);
                }
            }
        }

        // Make graph
        for (Position pos : map) {
            if (map.contains(pos.adjacent(Direction.Left))) {
                graph.addEdge(pos, pos.adjacent(Direction.Left));
            }
            if (map.contains(pos.adjacent(Direction.Up))) {
                graph.addEdge(pos, pos.adjacent(Direction.Up));
            }
        }

        DijkstraShortestPath<Position, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<Position, DefaultEdge> iPaths = dijkstraAlg.getPaths(new Position(1, 1));
        return iPaths.getPath(endPosition).getLength();

    }

    int numberOfLocations() {
        // Make map
        Set<Position> map = new HashSet<>();

        for (int y = 0; y < 55; y++) {
            for (int x = 0; x < 55; x++) {
                Position p = new Position(x, y);
                if (isOpenSpace(p)) {
                    map.add(p);
                    graph.addVertex(p);
                }
            }
        }

        // Make graph
        for (Position pos : map) {
            if (map.contains(pos.adjacent(Direction.Left))) {
                graph.addEdge(pos, pos.adjacent(Direction.Left));
            }
            if (map.contains(pos.adjacent(Direction.Up))) {
                graph.addEdge(pos, pos.adjacent(Direction.Up));
            }
        }

        DijkstraShortestPath<Position, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<Position, DefaultEdge> iPaths = dijkstraAlg.getPaths(new Position(1, 1));

        int locations = 0;
        for (Position p : map) {
            if (iPaths.getPath(p) != null && iPaths.getPath(p).getLength() <= 50) {
                locations++;
            }
        }
        return locations;
    }

    boolean isOpenSpace(Position p) {
        int value = p.getX() * p.getX() + 3 * p.getX() + 2 * p.getX() * p.getY() + p.getY() + p.getY() * p.getY() + favoriteNumber;

        String s = Integer.toBinaryString(value);
        int i = StringUtils.countMatches(s, "1");
        return ((i % 2) == 0);
    }
}
