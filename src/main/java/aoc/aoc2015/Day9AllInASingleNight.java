package aoc.aoc2015;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day9AllInASingleNight {

    private final Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

    public Day9AllInASingleNight(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^(\\w+) to (\\w+) = (\\d+)$");
        Matcher matcher;

        for (String line : inputLines) {
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                String start = matcher.group(1);
                String end = matcher.group(2);
                int distance = Integer.parseInt(matcher.group(3));

                if (!graph.containsVertex(start)) {
                    graph.addVertex(start);
                }
                if (!graph.containsVertex(end)) {
                    graph.addVertex(end);
                }
                graph.addEdge(start, end);
                graph.setEdgeWeight(start, end, distance);
            }
        }
    }

    int shortestRoute() {

        HeldKarpTSP<String, DefaultWeightedEdge> tour = new HeldKarpTSP<>();
        int shortestRoute = Integer.MAX_VALUE;

        // Change weight between start and end to 0 and compute length => that will give the shortest using TSP
        for (String start : graph.vertexSet()) {
            for (String end : graph.vertexSet()) {

                if (!start.equals(end)) {
                    DefaultWeightedEdge e = graph.getEdge(start, end);
                    double weight = graph.getEdgeWeight(e);
                    graph.setEdgeWeight(e, 0);
                    int length = (int) tour.getTour(graph).getWeight();
                    log.info("Length between {} and {}: {}", start, end, length);
                    if (length < shortestRoute) {
                        shortestRoute = length;
                    }
                    graph.setEdgeWeight(e, weight);
                }
            }
        }
        return shortestRoute;
    }


    int longestRoute() {
        return 0;
    }
}