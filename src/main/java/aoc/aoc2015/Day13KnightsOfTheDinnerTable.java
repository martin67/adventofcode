package aoc.aoc2015;

import com.google.common.collect.Collections2;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day13KnightsOfTheDinnerTable {

    private final Graph<String, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

    public Day13KnightsOfTheDinnerTable(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^(\\w+) would (gain|lose) (\\d+) happiness units by sitting next to (\\w+).$");

        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String guest = matcher.group(1);
                String neighbour = matcher.group(4);
                int happiness = Integer.parseInt(matcher.group(3));
                if (matcher.group(2).equals("lose")) {
                    happiness = -happiness;
                }
                if (!graph.containsVertex(guest)) {
                    graph.addVertex(guest);
                }
                if (!graph.containsVertex(neighbour)) {
                    graph.addVertex(neighbour);
                }

                DefaultWeightedEdge e = graph.addEdge(guest, neighbour);
                graph.setEdgeWeight(e, happiness);
            }
        }
    }

    public int changeInHappiness() {
        Collection<List<String>> permutations = Collections2.permutations(graph.vertexSet());
        int happiest = Integer.MIN_VALUE;
        for (List<String> permutation : permutations) {
            int happiness = getHappiness(permutation);
            log.debug("{} : {}", permutation, happiness);
            if (happiness > happiest) {
                happiest = happiness;
            }
        }
        return happiest;
    }

    public int changeInHappinessWithMe() {
        graph.addVertex("Me");
        for (String vertex : graph.vertexSet()) {
            if (!vertex.equals("Me")) {
                graph.addEdge("Me", vertex);
                graph.setEdgeWeight("Me", vertex, 0);
                graph.addEdge(vertex, "Me");
                graph.setEdgeWeight(vertex, "Me", 0);
            }
        }

        Collection<List<String>> permutations = Collections2.permutations(graph.vertexSet());
        int happiest = Integer.MIN_VALUE;
        for (List<String> permutation : permutations) {
            int happiness = getHappiness(permutation);
            log.debug("{} : {}", permutation, happiness);
            if (happiness > happiest) {
                happiest = happiness;
            }
        }
        return happiest;
    }

    int getHappiness(List<String> table) {
        int happiness = 0;
        for (int i = 0; i < table.size(); i++) {
            String left;
            String right;
            if (i == 0) {
                left = table.get(table.size() - 1);
            } else {
                left = table.get(i - 1);
            }
            if (i == table.size() - 1) {
                right = table.get(0);
            } else {
                right = table.get(i + 1);
            }
            happiness += graph.getEdgeWeight(graph.getEdge(table.get(i), left));
            happiness += graph.getEdgeWeight(graph.getEdge(table.get(i), right));
        }
        return happiness;
    }
}
