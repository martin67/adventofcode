package aoc.aoc2017;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.List;

public class Day12DigitalPlumber {
    private final Graph<Integer, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
    private final ConnectivityInspector<Integer, DefaultEdge> connectivityInspector = new ConnectivityInspector<>(graph);

    public Day12DigitalPlumber(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s = line.split(" <-> ");

            Integer source = Integer.parseInt(s[0]);
            if (!graph.containsVertex(source)) {
                graph.addVertex(source);
            }

            for (String d : s[1].split(", ")) {
                Integer target = Integer.parseInt(d);
                if (!graph.containsVertex(target)) {
                    graph.addVertex(target);
                }
                graph.addEdge(source, target);
            }
        }
    }

    int programsInGroup() {
        return connectivityInspector.connectedSetOf(0).size();
    }

    int numberOfGroups() {
        return connectivityInspector.connectedSets().size();
    }
}
