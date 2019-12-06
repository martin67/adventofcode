package aoc2019;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.List;

@Slf4j
public class Day6UniversalOrbitMap {

    private Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);

    public Day6UniversalOrbitMap(List<String> inputLines) {
        log.info("Adding nodes");
        for (String line : inputLines) {
            String[] objects = line.split("\\)");
            g.addVertex(objects[0]);
            g.addVertex(objects[1]);
            g.addEdge(objects[0], objects[1]);
        }
    }

    int numberOfOrbits() {
        int totalDistance = 0;

        log.info("Computing distances");
        for (String object : g.vertexSet()) {
            DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(g);
            ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths("COM");
            int distance = iPaths.getPath(object).getLength();
            log.info("Distance from {} to COM is {}", object, distance);
            totalDistance += distance;
        }

        return totalDistance;
    }

    int minimumNumberOfOrbits() {
        log.info("Computing distances");
        DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(g);
        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths("COM");
        int distance = iPaths.getPath("YOU").getLength();
        log.info("Distance from YOU to COM is {}", distance);
        distance -= 3;

        return distance;
    }
}
