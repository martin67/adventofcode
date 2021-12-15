package aoc.aoc2021;

import aoc.Position;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

@Slf4j
public class Day15Chiton {

    Map<Position, Node> map = new HashMap<>();
    Graph<Node, DefaultWeightedEdge> graph;
    int height;
    int width;

    public Day15Chiton(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                Position p = new Position(x, y);
                map.put(p, new Node(p, Character.getNumericValue(c)));
                x++;
            }
            width = x;
            y++;
        }
        height = y;

        graph = createGraph(map);
    }

    static class Node {
        Position position;
        int riskLevel;

        public Node(Position position, int riskLevel) {
            this.position = position;
            this.riskLevel = riskLevel;
        }

        int increaseRiskLevel(int value) {
            int newLevel = riskLevel + value;
            while (newLevel > 9) {
                newLevel -= 9;
            }
            return newLevel;
        }

        @Override
        public String toString() {
            return "{" + position.getX() + "," + position.getY() + " " + riskLevel + "}";
        }
    }

    int problem1() {
        DijkstraShortestPath<Node, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        Node start = map.get(new Position(0, 0));
        Node end = map.get(new Position(width - 1, height - 1));
        ShortestPathAlgorithm.SingleSourcePaths<Node, DefaultWeightedEdge> paths = dijkstraAlg.getPaths(start);
        GraphPath<Node, DefaultWeightedEdge> path = paths.getPath(end);

        int riskLevel = 0;
        for (Node n : path.getVertexList()) {
            riskLevel += n.riskLevel;
            //log.info("Node: {}, {}", n, riskLevel);
        }
        riskLevel -= start.riskLevel;
        return riskLevel;
    }

    int problem2() {
        // expand map
        Map<Position, Node> newMap = new HashMap<>();
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                for (Position p : map.keySet()) {
                    Position newPosition = new Position(p.getX() + x * width, p.getY() + y * height);
                    newMap.put(newPosition, new Node(newPosition, map.get(p).increaseRiskLevel(x + y)));
                }
            }
        }
        int newWidth = width * 5;
        int newHeight = height * 5;
        //printMap(newMap, newWidth, newHeight);

        Graph<Node, DefaultWeightedEdge> newGraph = createGraph(newMap);

        DijkstraShortestPath<Node, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(newGraph);
        Node start = newMap.get(new Position(0, 0));
        Node end = newMap.get(new Position(newWidth - 1, newHeight - 1));
        ShortestPathAlgorithm.SingleSourcePaths<Node, DefaultWeightedEdge> paths = dijkstraAlg.getPaths(start);
        GraphPath<Node, DefaultWeightedEdge> path = paths.getPath(end);
        int riskLevel = 0;
        for (Node n : path.getVertexList()) {
            riskLevel += n.riskLevel;
            //log.info("Node: {}, {}", n, riskLevel);
        }
        riskLevel -= start.riskLevel;
        return riskLevel;
    }

    private Graph<Node, DefaultWeightedEdge> createGraph(Map<Position, Node> map) {
        Graph<Node, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        for (Position p : map.keySet()) {
            if (!graph.containsVertex(map.get(p))) {
                graph.addVertex(map.get(p));
            }
            for (Position adj : p.allAdjacent()) {
                if (map.containsKey(adj)) {
                    if (!graph.containsVertex(map.get(adj))) {
                        graph.addVertex(map.get(adj));
                    }
                    graph.addEdge(map.get(p), map.get(adj));
                    graph.setEdgeWeight(map.get(p), map.get(adj), map.get(p).riskLevel + map.get(adj).riskLevel);
                }
            }
        }
        return graph;
    }

    void printMap(Map<Position, Node> map, int width, int height) {
        for (int y = 0; y < height * 5; y++) {
            for (int x = 0; x < width * 5; x++) {
                System.out.print(map.get(new Position(x, y)).riskLevel);
            }
            System.out.println();
        }
        System.out.println();
    }
}
