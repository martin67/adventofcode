package aoc.aoc2018;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Day25FourDimensionalAdventure {

    @Data
    @AllArgsConstructor
    static class SpaceTimePosition {
        int x;
        int y;
        int z;
        int t;

        int distance(SpaceTimePosition p) {
            return Math.abs(x - p.x) + Math.abs(y - p.y) + Math.abs(z - p.z) + Math.abs(t - p.t);
        }
    }

    private List<SpaceTimePosition> spacetime = new ArrayList<>();

    private Graph<SpaceTimePosition, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

    public Day25FourDimensionalAdventure(String fileName) throws IOException {
        readData(fileName);
        setupGraph();
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        for (String row : inputStrings) {
            String[] s = row.split(",");
            spacetime.add(new SpaceTimePosition(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3])
            ));
        }
    }

    private void setupGraph() {
        System.out.println("Setting up graph; adding vertices");
        spacetime.forEach(n -> graph.addVertex(n));

        System.out.println("Setting up graph; adding edges");
        for (SpaceTimePosition stp1 : spacetime) {
            for (SpaceTimePosition stp2 : spacetime) {
                if (!stp1.equals(stp2) && stp1.distance(stp2) <= 3) {
                    graph.addEdge(stp1, stp2);
                }
            }
        }
        System.out.println("Number of vertexes: " + graph.vertexSet().size());
        System.out.println("Number of edges: " + graph.edgeSet().size());
    }

    int numberOfConstellations() {
        ConnectivityInspector<SpaceTimePosition, DefaultEdge> connectivityInspector = new ConnectivityInspector<>(graph);

        List<Set<SpaceTimePosition>> constellations = connectivityInspector.connectedSets();
        return constellations.size();
    }
}
