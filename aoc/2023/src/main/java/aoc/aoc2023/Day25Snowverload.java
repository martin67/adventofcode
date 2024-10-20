package aoc.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.List;

@Slf4j
public class Day25Snowverload {

    private final Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

    Day25Snowverload(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s1 = line.split(":");
            String component = s1[0];
            graph.addVertex(component);
            for (String s2 : s1[1].trim().split("\\s+")) {
                graph.addVertex(s2);
                graph.addEdge(s1[0], s2);
            }
        }
    }

    int problem1() {
        return 0;
    }

}
