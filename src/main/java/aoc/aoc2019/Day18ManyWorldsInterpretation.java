package aoc.aoc2019;

import aoc.Direction;
import aoc.Position;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day18ManyWorldsInterpretation {

    static class Walker {
        static int maxId = 1;
        final int id;
        final BiMap<Position, Character> keys;
        final BiMap<Position, Character> doors;
        final Character start;
        final Graph<Character, DefaultWeightedEdge> graph;

        public Walker(BiMap<Position, Character> keys, BiMap<Position, Character> doors, Character start, Graph<Character, DefaultWeightedEdge> graph) {
            this.id = maxId++;
            this.keys = keys;
            this.doors = doors;
            this.start = start;
            this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
            Graphs.addGraph(this.graph, graph);
        }

        // return all reachable keys and their distance
        Map<Character, Integer> getAllReachableKeys() {
            log.debug("[{}] Evaluating all reachable keys from {}", id, start);
            DijkstraShortestPath<Character, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
            ShortestPathAlgorithm.SingleSourcePaths<Character, DefaultWeightedEdge> iPaths = dijkstraAlg.getPaths(start);
            Map<Character, Integer> result = new HashMap<>();
            for (Character key : keys.values()) {
                double distance = iPaths.getPath(key).getWeight();
                log.debug("[{}] Distance from start {} to {}: {}", id, start, key, distance);
                if (!doorsOnPath(iPaths.getPath(key).getVertexList(), doors.inverse().keySet())) {
                    result.put(key, (int) iPaths.getPath(key).getWeight());
                }
            }
            return result;
        }

        boolean doorsOnPath(List<Character> path, Set<Character> doors) {
            List<Character> workingPath = new ArrayList<>(path);
            boolean foundDoor = false;
            // Remove last and first
            workingPath.remove(path.size() - 1);
            workingPath.remove(0);
            for (Character charToCheck : workingPath) {
                log.debug("Checking {}", charToCheck);
                if (doors.contains(charToCheck)) {
                    log.debug("Found door at {}, skipping", charToCheck);
                    foundDoor = true;
                    break;
                }
            }
            return foundDoor;
        }

        WalkResult shortestPathRecursive() {
            // Find all reachable keys and their distance
            Map<Character, Integer> reachableKeys = getAllReachableKeys();

            // Check that we haven't tried this path before
            String cacheKey = start + reachableKeys.keySet().stream().sorted().map(String::valueOf).collect(Collectors.joining());
            if (mapCache.containsKey(cacheKey)) {
                log.debug("[{}] Found cache hit for {}", id, cacheKey);
                return mapCache.get(cacheKey);
            }

            // find the shortest path for all those keys
            // goto key and make the door possible to open (reduce weight)
            Set<WalkResult> walkResults = new HashSet<>();
            for (Character key : reachableKeys.keySet()) {
                WalkResult walkResult = new WalkResult();
                BiMap<Position, Character> newKeys = HashBiMap.create(keys);
                BiMap<Position, Character> newDoors = HashBiMap.create(doors);
                Graph<Character, DefaultWeightedEdge> newGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
                Graphs.addGraph(newGraph, graph);

                walkResult.length = reachableKeys.get(key);
                walkResult.path.add(key);
                log.debug("[{}] Going from {} to key {}, distance {}", id, start, key, walkResult.length);
                newKeys.inverse().remove(key);
                if (newKeys.isEmpty()) {
                    log.debug("All keys collected - returning");
                } else {
                    // open the door
                    Character door = Character.toUpperCase(key);
                    if (newDoors.containsValue(door)) {
                        log.debug("[{}] Opening door {}", id, door);
                        for (DefaultWeightedEdge edge : newGraph.edgesOf(door)) {
                            int currentWeight = (int) newGraph.getEdgeWeight(edge);
                            if (currentWeight > DEFAULT_DOOR_WEIGHT) {
                                newGraph.setEdgeWeight(edge, currentWeight - DEFAULT_DOOR_WEIGHT);
                            }
                        }
                        newDoors.inverse().remove(door);
                    }
                    // and continue
                    log.debug("[{}] Creating new walker", id);
                    Walker walker = new Walker(newKeys, newDoors, key, newGraph);
                    //length += walker.shortestPathRecursive();
                    WalkResult wr = walker.shortestPathRecursive();
                    walkResult.length += wr.length;
                    walkResult.path.addAll(wr.path);

                }
                walkResults.add(walkResult);
            }
            // return the shortest
            log.debug("[{}] Returning from walker, {}", id, walkResults);
            WalkResult shortestWalk = walkResults.stream().min(Comparator.comparing(WalkResult::getLength)).orElse(null);
            mapCache.put(cacheKey, shortestWalk);
            return shortestWalk;
        }
    }

    @Data
    static class WalkResult {
        int length;
        List<Character> path = new ArrayList<>();
    }

    final HashBiMap<Position, Character> keys = HashBiMap.create();
    final HashBiMap<Position, Character> doors = HashBiMap.create();
    Position start;
    private final Graph<Position, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    private final Graph<Character, DefaultWeightedEdge> graph2 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    private final static int DEFAULT_DOOR_WEIGHT = 10000000;
    private final static Map<String, WalkResult> mapCache = new HashMap<>();

    public Day18ManyWorldsInterpretation(List<String> inputLines) {
        Set<Position> map = new HashSet<>();
        int x;
        int y = 0;
        Position pos;
        mapCache.clear();

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
                            doors.put(pos, c);
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
        for (Position doorPosition : doors.inverse().values()) {
            for (DefaultWeightedEdge edge : graph.edgesOf(doorPosition)) {
                graph.setEdgeWeight(edge, DEFAULT_DOOR_WEIGHT);
            }
        }

        log.info("Creating new graph ------------------");
        // Create new graph with only the connected nodes
        Set<Position> allNodes = new HashSet<>();
        allNodes.addAll(keys.keySet());
        allNodes.addAll(doors.keySet());
        allNodes.add(start);
        for (Position position : allNodes) {
            Character c;
            if (keys.containsKey(position)) {
                c = keys.get(position);
            } else c = doors.getOrDefault(position, '@');
            log.debug("Evaluating: {} at pos {}", c, position);

            if (!graph2.containsVertex(c)) {
                log.debug("adding 1st vertex, {} at pos {}", c, position);
                graph2.addVertex(c);
            }

            Map<Position, Integer> nodes = getAllReachableNodes(position);

            for (Position p : nodes.keySet()) {
                Character c2;
                if (keys.containsKey(p)) {
                    c2 = keys.get(p);
                } else c2 = doors.getOrDefault(p, '@');

                if (!graph2.containsVertex(c2)) {
                    log.debug("adding 2nd vertex, {} at pos {}", c2, p);
                    graph2.addVertex(c2);
                }
                DefaultWeightedEdge e = graph2.addEdge(c, c2);
                if (e != null) {
                    int weight = nodes.get(p);
                    if (doors.containsValue(c) || doors.containsValue(c2)) {
                        log.debug("One node is a door, adding weight {}", DEFAULT_DOOR_WEIGHT);
                        weight += DEFAULT_DOOR_WEIGHT;
                    }
                    graph2.setEdgeWeight(e, weight);
                    log.debug("adding edge between {} and {}, distance {}", c, c2, weight);
                }
            }
        }
        log.info("-----------------------------------");
    }

    // return all reachable nodes and their distance
    Map<Position, Integer> getAllReachableNodes(Position start) {
        log.debug("Evaluating all reachable nodes from {}", start);
        Set<Position> allNodes = new HashSet<>();
        allNodes.addAll(keys.keySet());
        allNodes.addAll(doors.keySet());

        DijkstraShortestPath<Position, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<Position, DefaultWeightedEdge> iPaths = dijkstraAlg.getPaths(start);
        Map<Position, Integer> result = new HashMap<>();

        for (Position position : allNodes) {
            if (position != start) {
                double distance = iPaths.getPath(position).getWeight();
                log.debug("Distance from start {} to {}: {}", start, position, distance);
                // Check that there are no doors on the path
                if (!doorsOnPath(iPaths.getPath(position).getVertexList(), doors.keySet())) {
                    log.debug("Adding position {} to result", position);
                    result.put(position, iPaths.getPath(position).getLength());
                }
            }
        }

        for (Position p : result.keySet()) {
            log.debug("Node {} is connected to {}, distance {}", start, p, result.get(p));
        }
        return result;
    }

    boolean doorsOnPath(List<Position> path, Set<Position> doors) {
        List<Position> workingPath = new ArrayList<>(path);
        boolean foundDoor = false;
        // Remove last and first
        workingPath.remove(path.size() - 1);
        workingPath.remove(0);
        for (Position posToCheck : workingPath) {
            log.debug("Checking {}", posToCheck);
            if (doors.contains(posToCheck)) {
                log.debug("Found door at {}, skipping", posToCheck);
                foundDoor = true;
                break;
            }
        }
        return foundDoor;
    }

    int shortestPath() {
        Walker walker = new Walker(keys, doors, '@', graph2);
        return walker.shortestPathRecursive().length;
    }
}
