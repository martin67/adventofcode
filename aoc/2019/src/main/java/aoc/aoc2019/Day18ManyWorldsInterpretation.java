package aoc.aoc2019;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day18ManyWorldsInterpretation {

    private final static Map<String, Integer> pathCache = new HashMap<>();
    private final Map<Position, Character> keys = new HashMap<>();
    private final Map<Position, Character> doors = new HashMap<>();
    private final Map<Position, Character> starts = new HashMap<>();
    private final Graph<Position, DefaultWeightedEdge> initialGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    private final Graph<Character, DefaultWeightedEdge> reducedGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

    public Day18ManyWorldsInterpretation(List<String> inputLines) {
        Set<Position> map = new HashSet<>();
        int y = 0;
        Position pos;
        List<Position> startPositions = new ArrayList<>();

        pathCache.clear();

        log.info("Creating initial graph");
        for (String line : inputLines) {
            int x = 0;
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
                        startPositions.add(pos);
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

        // Convert startPositions from @ to 0,1,2,3 etc.
        for (int i = 0; i < startPositions.size(); i++) {
            starts.put(startPositions.get(i), Character.forDigit(i, 10));
        }

        // Create new graph with only the connected nodes
        log.info("Creating reduced graph");
        Set<Position> allNodes = new HashSet<>();
        allNodes.addAll(keys.keySet());
        allNodes.addAll(doors.keySet());
        allNodes.addAll(starts.keySet());
        for (Position startPosition : allNodes) {
            Character start;
            if (keys.containsKey(startPosition)) {
                start = keys.get(startPosition);
            } else if (doors.containsKey(startPosition)) {
                start = doors.get(startPosition);
            } else {
                start = starts.get(startPosition);
            }
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
                } else if (doors.containsKey(endPosition)) {
                    end = doors.get(endPosition);
                } else {
                    end = starts.get(endPosition);
                }

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
                if (path != null) {
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
        }
        return result;
    }

    int shortestPath() {
        log.info("Creating initial walker");
        return new Walker(keys.values(), doors.values(), "0", reducedGraph).findShortestPath();
    }

    int shortestMultiplePath() {
        log.info("Creating initial walker");
        return new Walker(keys.values(), doors.values(), "0123", reducedGraph).findShortestPath();
    }

    static class Walker {
        final Collection<Character> keys;
        final Collection<Character> doors;
        final String start;
        final Graph<Character, DefaultWeightedEdge> graph;
        final DijkstraShortestPath<Character, DefaultWeightedEdge> dijkstraAlg;

        public Walker(Collection<Character> keys, Collection<Character> doors, String start, Graph<Character, DefaultWeightedEdge> graph) {
            this.keys = keys;
            this.doors = doors;
            this.start = start;
            this.graph = graph;
            this.dijkstraAlg = new DijkstraShortestPath<>(graph);
        }

        // return all reachable keys and their distance
        Map<Character, Map<Character, Integer>> getAllReachableKeys(String startPositions) {
            Map<Character, Map<Character, Integer>> totalResult = new HashMap<>();
            for (Character start : startPositions.toCharArray()) {
                ShortestPathAlgorithm.SingleSourcePaths<Character, DefaultWeightedEdge> iPaths = dijkstraAlg.getPaths(start);
                Map<Character, Integer> result = new HashMap<>();
                for (Character key : keys) {
                    // position is reachable if there are no doors on the shortest path
                    GraphPath<Character, DefaultWeightedEdge> path = iPaths.getPath(key);
                    if (path != null) {
                        List<Character> pathNodes = path.getVertexList();
                        // remove first and last (start and end)
                        pathNodes.remove(pathNodes.size() - 1);
                        pathNodes.remove(0);

                        boolean doorOnPath = false;
                        for (Character c : pathNodes) {
                            if (doors.contains(c)) {
                                doorOnPath = true;
                                break;
                            }
                        }

                        if (!doorOnPath) {
                            log.debug("Distance from start {} to {}: {}", start, key, path.getWeight());
                            result.put(key, (int) path.getWeight());
                        }
                    }
                    totalResult.put(start, result);
                }
            }
            return totalResult;
        }

        int findShortestPath() {
            // Find all reachable keys and their distance
            Map<Character, Map<Character, Integer>> allReachableKeys = getAllReachableKeys(start);

            // Check that we haven't tried this path before
            StringBuilder cacheKey = new StringBuilder();
            cacheKey.append(start);
            for (Character c : start.toCharArray()) {
                Map<Character, Integer> rk = allReachableKeys.get(c);
                if (rk != null) {
                    cacheKey.append(rk.keySet().stream().sorted().map(String::valueOf).collect(Collectors.joining()));
                } else {
                    cacheKey.append("-");
                }
            }
            if (pathCache.containsKey(cacheKey.toString())) {
                return pathCache.get(cacheKey.toString());
            }

            // find the shortest path for all those keys
            // goto key and open the door
            int shortestPath = Integer.MAX_VALUE;

            //for (Character s : start.toCharArray()) {
            for (int i = 0; i < start.length(); i++) {
                Character s = start.charAt(i);
                Map<Character, Integer> reachableKeys = allReachableKeys.get(s);
                if (reachableKeys != null) {
                    for (Character key : reachableKeys.keySet()) {
                        Set<Character> newKeys = new HashSet<>(keys);
                        Set<Character> newDoors = new HashSet<>(doors);

                        int path = reachableKeys.get(key);
                        newKeys.remove(key);
                        if (!newKeys.isEmpty()) {
                            // open the door. Does not matter if there is no door
                            newDoors.remove(Character.toUpperCase(key));
                            // and continue
                            char[] newStart = start.toCharArray();
                            newStart[i] = key;
                            path += new Walker(newKeys, newDoors, String.valueOf(newStart), graph).findShortestPath();
                        }
                        if (path < shortestPath) {
                            shortestPath = path;
                        }
                    }
                    pathCache.put(cacheKey.toString(), shortestPath);
                }
            }
            return shortestPath;
        }
    }

}
