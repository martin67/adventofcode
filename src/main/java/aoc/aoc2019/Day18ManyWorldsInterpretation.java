package aoc.aoc2019;

import aoc.Direction;
import aoc.Position;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

@Slf4j
public class Day18ManyWorldsInterpretation {

    static class Walker {
        static int maxId = 1;
        final int id;
        final Map<Position, Character> keys;
        final Map<Character, Position> doors;
        final Position start;
        final Graph<Position, DefaultWeightedEdge> graph;

        public Walker(Map<Position, Character> keys, Map<Character, Position> doors, Position start, Graph<Position, DefaultWeightedEdge> graph) {
            this.id = maxId++;
            this.keys = new HashMap<>(keys);
            this.doors = new HashMap<>(doors);
            this.start = start;
            this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
            Graphs.addGraph(this.graph, graph);
        }

        // return all reachable keys and their distance
        Map<Position, Integer> getAllReachableKeys() {
            log.info("[{}] Evaluating all reachable keys from {}", id, start);
            DijkstraShortestPath<Position, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
            ShortestPathAlgorithm.SingleSourcePaths<Position, DefaultWeightedEdge> iPaths = dijkstraAlg.getPaths(start);
            Map<Position, Integer> result = new HashMap<>();
            for (Position keyPosition : keys.keySet()) {
                double distance = iPaths.getPath(keyPosition).getWeight();
                log.info("[{}] Distance from start {} to {} {}:  {}", id, start, keys.get(keyPosition), keyPosition, distance);
                if (distance < DEFAULT_DOOR_WEIGHT) {
                    result.put(keyPosition, iPaths.getPath(keyPosition).getLength());
                }
            }
            return result;
        }

        WalkResult shortestPathRecursive() {
            // Find all reachable keys and their distance
            Map<Position, Integer> reachableKeys = getAllReachableKeys();

            // find the shortest path for all those keys
            // goto key and make the door possible to open (reduce weight)
            //Set<Integer> lengths = new HashSet<>();
            Set<WalkResult> walkResults = new HashSet<>();
            for (Position keyPosition : reachableKeys.keySet()) {
                WalkResult walkResult = new WalkResult();
                Map<Position, Character> newKeys = new HashMap<>(keys);
                Map<Character, Position> newDoors = new HashMap<>(doors);
                Graph<Position, DefaultWeightedEdge> newGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
                Graphs.addGraph(newGraph, graph);

                Character keyName = newKeys.get(keyPosition);
                walkResult.length = reachableKeys.get(keyPosition);
                walkResult.path.add(keyName);
                log.info("[{}] Going from {} to key {} at {}, distance {}", id, start, newKeys.get(keyPosition), keyPosition, walkResult);
                newKeys.remove(keyPosition);
                if (newKeys.isEmpty()) {
                    log.info("All keys collected - returning");
                } else {
                    // open the door
                    if (newDoors.containsKey(keyName)) {
                        Position doorPosition = newDoors.get(keyName);
                        log.info("[{}] Opening door {} at {}", id, keyName, doorPosition);
                        for (DefaultWeightedEdge edge : newGraph.edgesOf(doorPosition)) {
                            newGraph.setEdgeWeight(edge, 1);
                        }
                        newDoors.remove(keyName);
                    }
                    // and continue
                    log.info("[{}] Creating new walker", id);
                    Walker walker = new Walker(newKeys, newDoors, keyPosition, newGraph);
                    //length += walker.shortestPathRecursive();
                    WalkResult wr = walker.shortestPathRecursive();
                    walkResult.length += wr.length;
                    walkResult.path.addAll(wr.path);
                }
                walkResults.add(walkResult);
            }
            // return the shortest
            log.info("[{}] Returning from walker, {}", id, walkResults);
            //WalkResult walkResult = new WalkResult(Collections.min(lengths), null);
            //WalkResult walkResult = new WalkResult(Collections.min(lengths), null);
            return walkResults.stream().min(Comparator.comparing(WalkResult::getLength)).orElse(null);
        }
    }

    static class Walker2 {
        public Walker2(Map<Position, Character> keys, Map<Position, Character> doors, Position start, Graph<Character, DefaultWeightedEdge> graph) {
        }
    }

    @Data
    static class WalkResult {
        int length;
        List<Character> path = new ArrayList<>();
    }

    final Map<Position, Character> keys = new HashMap<>();
    final Map<Character, Position> doors = new HashMap<>();
    final Map<Position, Character> doors2 = new HashMap<>();
    Position start;
    private final Graph<Position, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    private final Graph<Character, DefaultWeightedEdge> graph2 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    private final static int DEFAULT_DOOR_WEIGHT = 10000;

    public Day18ManyWorldsInterpretation(List<String> inputLines) {
        Set<Position> map = new HashSet<>();
        int x;
        int y = 0;
        Position pos;
        for (String line : inputLines) {
            x = 0;
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
                    if (c == '@') {
                        start = pos;
                    } else if (c != '.') {
                        if (Character.isUpperCase(c)) {
                            doors.put(c, pos);
                            doors2.put(pos, c);
                        } else {
                            keys.put(pos, c);
                        }
                    }
                }
                x++;
            }
            y++;
        }

        // Make doors have heavy weight
        for (Position doorPosition : doors.values()) {
            for (DefaultWeightedEdge edge : graph.edgesOf(doorPosition)) {
                graph.setEdgeWeight(edge, DEFAULT_DOOR_WEIGHT);
            }
        }

        // Create new graph with only the connected nodes
        Set<Position> allNodes = new HashSet<>();
        allNodes.addAll(keys.keySet());
        allNodes.addAll(doors2.keySet());
        allNodes.add(start);
        for (Position position : allNodes) {
            Character c;
            if (keys.containsKey(position)) {
                c = keys.get(position);
            } else if (doors2.containsKey(position)) {
                c = doors2.get(position);
            } else {
                c = '@';
            }
            log.info("Evaluating: {} at pos {}", c, position);

            if (!graph2.containsVertex(c)) {
                log.info("adding 1st vertex, {} at pos {}", c, position);
                graph2.addVertex(c);
            }

            Map<Position, Integer> nodes = getAllReachableNodes(position);

            for (Position p : nodes.keySet()) {
                Character c2;
                if (keys.containsKey(p)) {
                    c2 = keys.get(p);
                } else if (doors2.containsKey(p)) {
                    c2 = doors2.get(p);
                } else {
                    c2 = '@';
                }

                if (!graph2.containsVertex(c2)) {
                    log.info("adding 2nd vertex, {} at pos {}", c2, p);
                    graph2.addVertex(c2);
                }
                log.info("adding edge between {} and {} ({}, {}), distance {}", c, c2, position, p, nodes.get(p));
                DefaultWeightedEdge e = graph2.addEdge(c, c2);
                if (e != null) {
                    graph2.setEdgeWeight(e, nodes.get(p));
                }
            }
        }

    }

    // return all reachable nodes and their distance
    Map<Position, Integer> getAllReachableNodes(Position start) {
        log.info("Evaluating all reachable nodes from {}", start);
        DijkstraShortestPath<Position, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<Position, DefaultWeightedEdge> iPaths = dijkstraAlg.getPaths(start);
        Map<Position, Integer> result = new HashMap<>();
        for (Position keyPosition : keys.keySet()) {
            if (keyPosition != start) {
                double distance = iPaths.getPath(keyPosition).getWeight();
                log.debug("Distance from start {} to key {} {}:  {}", start, keys.get(keyPosition), keyPosition, distance);
                if (distance < DEFAULT_DOOR_WEIGHT * 2) {
                    result.put(keyPosition, iPaths.getPath(keyPosition).getLength());
                }
            }
        }
        for (Position doorPosition : doors.values()) {
            if (doorPosition != start) {
                double distance = iPaths.getPath(doorPosition).getWeight();
                log.debug("Distance from start {} to door {} {}:  {}", start, doors.get(doorPosition), doorPosition, distance);
                if (distance < DEFAULT_DOOR_WEIGHT * 2) {
                    result.put(doorPosition, iPaths.getPath(doorPosition).getLength());
                }
            }
        }
        for (Position p : result.keySet()) {
            log.info("Node {} is connected to {}, distance {}", start, p, result.get(p));
        }
        return result;
    }

    int shortestPath() {
        Walker walker = new Walker(keys, doors, start, graph);
        return walker.shortestPathRecursive().length;
    }

}
