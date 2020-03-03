package aoc.aoc2019;

import aoc.Direction;
import aoc.Position;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
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
        final BiMap<Position, Character> keys;
        final BiMap<Position, Character> doors;
        final Character start;
        final Graph<Character, DefaultWeightedEdge> graph;
        final DijkstraShortestPath<Character, DefaultWeightedEdge> dijkstraAlg;

        public Walker(BiMap<Position, Character> keys, BiMap<Position, Character> doors, Character start, Graph<Character, DefaultWeightedEdge> graph) {
            this.keys = keys;
            this.doors = doors;
            this.start = start;
            this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
            Graphs.addGraph(this.graph, graph);
            this.dijkstraAlg = new DijkstraShortestPath<>(graph);
        }

        // return all reachable keys and their distance
        Map<Character, Integer> getAllReachableKeys() {
            ShortestPathAlgorithm.SingleSourcePaths<Character, DefaultWeightedEdge> iPaths = dijkstraAlg.getPaths(start);
            Map<Character, Integer> result = new HashMap<>();
            for (Character key : keys.values()) {
                // position is reachable if there are no doors on the shortest path
                GraphPath<Character, DefaultWeightedEdge> path = iPaths.getPath(key);
                List<Character> pathNodes = path.getVertexList();
                // remove first and last (start and end)
                pathNodes.remove(pathNodes.size() - 1);
                pathNodes.remove(0);

                boolean doorOnPath = false;
                for (Character c : pathNodes) {
                    if (doors.containsValue(c)) {
                        doorOnPath = true;
                        break;
                    }
                }

                if (!doorOnPath) {
                    log.debug("Distance from start {} to {}: {}", start, key, path.getWeight());
                    result.put(key, (int) path.getWeight());
                }
            }
            return result;
        }

        int findShortestPath() {
            // Find all reachable keys and their distance
            Map<Character, Integer> reachableKeys = getAllReachableKeys();

            // Check that we haven't tried this path before
            String cacheKey = start + reachableKeys.keySet().stream().sorted().map(String::valueOf).collect(Collectors.joining());
            if (pathCache.containsKey(cacheKey)) {
                return pathCache.get(cacheKey);
            }

            // find the shortest path for all those keys
            // goto key and make the door possible to open (reduce weight)
            int shortestPath = Integer.MAX_VALUE;

            for (Character key : reachableKeys.keySet()) {
                BiMap<Position, Character> newKeys = HashBiMap.create(keys);
                BiMap<Position, Character> newDoors = HashBiMap.create(doors);
                Graph<Character, DefaultWeightedEdge> newGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
                Graphs.addGraph(newGraph, graph);

                int path = reachableKeys.get(key);
                newKeys.inverse().remove(key);
                if (!newKeys.isEmpty()) {
                    // open the door
                    Character door = Character.toUpperCase(key);
                    if (newDoors.containsValue(door)) {
                        newDoors.inverse().remove(door);
                    }
                    // and continue
                    path += new Walker(newKeys, newDoors, key, newGraph).findShortestPath();
                }
                if (path < shortestPath) {
                    shortestPath = path;
                }
            }
            pathCache.put(cacheKey, shortestPath);
            return shortestPath;
        }
    }


    final HashBiMap<Position, Character> keys = HashBiMap.create();
    final HashBiMap<Position, Character> doors = HashBiMap.create();
    Position start;
    private final Graph<Position, DefaultWeightedEdge> initialGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    private final Graph<Character, DefaultWeightedEdge> reducedGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    private final static Map<String, Integer> pathCache = new HashMap<>();

    public Day18ManyWorldsInterpretation(List<String> inputLines) {
        Set<Position> map = new HashSet<>();
        int x;
        int y = 0;
        Position pos;

        pathCache.clear();

        for (String line : inputLines) {
            x = 0;
            for (char c : line.toCharArray()) {
                if (c != '#') {
                    pos = new Position(x, y);
                    map.add(pos);
                    initialGraph.addVertex(pos);
                    Position left = pos.adjacent(Direction.Left);
                    if (map.contains(left)) {
                        initialGraph.addEdge(pos, left);
                    }
                    Position up = pos.adjacent(Direction.Up);
                    if (map.contains(up)) {
                        initialGraph.addEdge(pos, up);
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

        // Create new graph with only the connected nodes
        Set<Position> allNodes = new HashSet<>();
        allNodes.addAll(keys.keySet());
        allNodes.addAll(doors.keySet());
        allNodes.add(start);
        for (Position startPosition : allNodes) {
            Character start;
            if (keys.containsKey(startPosition)) {
                start = keys.get(startPosition);
            } else start = doors.getOrDefault(startPosition, '@');
            log.debug("Evaluating: {} at pos {}", start, startPosition);

            if (!reducedGraph.containsVertex(start)) {
                log.debug("adding 1st vertex, {} at pos {}", start, startPosition);
                reducedGraph.addVertex(start);
            }

            Map<Position, Integer> nodes = getAllReachableNodes(startPosition, allNodes);

            for (Position endPosition : nodes.keySet()) {
                Character end;
                if (keys.containsKey(endPosition)) {
                    end = keys.get(endPosition);
                } else end = doors.getOrDefault(endPosition, '@');

                if (!reducedGraph.containsVertex(end)) {
                    log.debug("adding 2nd vertex, {} at pos {}", end, endPosition);
                    reducedGraph.addVertex(end);
                }
                DefaultWeightedEdge e = reducedGraph.addEdge(start, end);
                if (e != null) {
                    int weight = nodes.get(endPosition);
                    reducedGraph.setEdgeWeight(e, weight);
                    log.debug("adding edge between {} and {}, weight {}", start, end, weight);
                }
            }
        }
    }

    // return all reachable nodes and their distance
    Map<Position, Integer> getAllReachableNodes(Position start, Set<Position> allNodes) {
        log.debug("Evaluating all reachable nodes from {}", start);

        DijkstraShortestPath<Position, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(initialGraph);
        ShortestPathAlgorithm.SingleSourcePaths<Position, DefaultWeightedEdge> iPaths = dijkstraAlg.getPaths(start);
        Map<Position, Integer> result = new HashMap<>();

        for (Position position : allNodes) {
            if (position != start) {
                // position is reachable if there is no other nodes on the shortest path
                GraphPath<Position, DefaultWeightedEdge> path = iPaths.getPath(position);
                List<Position> pathPositions = path.getVertexList();
                // remove first and last (start and end)
                pathPositions.remove(pathPositions.size() - 1);
                pathPositions.remove(0);

                boolean nodesOnPath = false;
                for (Position p : pathPositions) {
                    if (allNodes.contains(p)) {
                        nodesOnPath = true;
                        break;
                    }
                }

                if (!nodesOnPath) {
                    log.debug("Distance from start {} to {}: {}", start, position, path.getLength());
                    result.put(position, path.getLength());
                }
            }
        }
        return result;
    }


    int shortestPath() {
        return new Walker(keys, doors, '@', reducedGraph).findShortestPath();
    }
}
