package aoc.aoc2022;

import com.google.common.collect.Collections2;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class Day16ProboscideaVolcanium {
    final Map<String, Valve> valves = new HashMap<>();
    final Graph<Valve, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    final DijkstraShortestPath<Valve, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);

    Day16ProboscideaVolcanium(List<String> inputLines) {
        var pattern = Pattern.compile("Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? ((\\w+)(,\\s*\\w+)*)");

        for (String line : inputLines) {
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                String valveName = matcher.group(1);
                int flowRate = Integer.parseInt((matcher.group(2)));
                var valve = valves.computeIfAbsent(valveName, v -> new Valve(valveName));
                valve.flowRate = flowRate;
                for (String a : matcher.group(3).split(", ")) {
                    var v = valves.computeIfAbsent(a, vv -> new Valve(a));
                    valve.tunnels.add(v);
                }
            }
        }
        log.info("Valves read: {}", valves.size());
        for (var valve : valves.values()) {
            graph.addVertex(valve);
        }
        for (var valve : valves.values()) {
            for (Valve v : valve.tunnels) {
                graph.addEdge(valve, v);
            }
        }

        log.info("Computing all distances");
        for (var valve : valves.values()) {
            ShortestPathAlgorithm.SingleSourcePaths<Valve, DefaultEdge> paths = dijkstraAlg.getPaths(valve);
            for (var v : valves.values()) {
                if (v != valve) {
                    int distance = paths.getPath(v).getLength();
                    valve.distances.put(v, distance);
                    //log.info("Distance from {} to {}: {}", valve, v, distance);
                }
            }
        }

    }


    int problem1() {
        var aa = valves.get("AA");
        // Have all 0 valves listed as already open
        var valvesToOpen = valves.values().stream().filter(v -> v.flowRate > 0).toList();
        log.info("Valves that can be opened: {} ({})", valvesToOpen, valvesToOpen.size());
        for (var v : aa.distances.keySet()) {
            log.info("Distance from {} to {}: {}", aa, v, aa.distances.get(v));
        }

        Collection<List<Valve>> hej = Collections2.permutations(valvesToOpen);
        log.info("Permutations: {}", hej.size());
        int maxFlow = 0;
        for (var path : hej) {
            int totalFlow = 0;
            Valve start = aa;
            int minutes = 30;
            for (var v : path) {
                int distance = start.distances.get(v);
                totalFlow += (minutes - (distance + 1)) * v.flowRate;
                minutes -= (distance + 1);
                start = v;
            }
            //maxFlow = Math.max(maxFlow, totalFlow);
            if (totalFlow > maxFlow) {
                maxFlow = totalFlow;
                log.info("max flow: {}, path: {}", maxFlow, path);
                StringBuilder sb = new StringBuilder();
                sb.append(path.getFirst());
                for (int i = 0; i < path.size() - 1; i++) {
                    var src = path.get(i);
                    var dst = path.get(i + 1);
                    sb.append("->").append(src.distances.get(dst)).append("->").append(dst);
                }
                log.info(sb.toString());
            }
        }

//        Set<Valve> flowValves = valves.values().stream().filter(v -> v.flowRate > 0).collect(ImmutableSet.toImmutableSet());
//        Set<Set<Valve>> allPossiblePaths = Sets.powerSet(flowValves);

        //return aa.computeFlow(openValves, 0,0, 24);
        return maxFlow;
    }

    int problem2() {
        return 0;
    }

    void getDistanceCost(Valve valve, int minutes) {
        ShortestPathAlgorithm.SingleSourcePaths<Valve, DefaultEdge> paths = dijkstraAlg.getPaths(valve);
        for (var v : valves.values()) {
            if (v != valve) {
                int distance = paths.getPath(v).getLength();
                log.info("Distance from {} to {}: {}", valve, v, distance);
                log.info("cost value: ({} - {} - 1) x {} = {}", minutes, paths.getPath(v).getLength(), v.flowRate,
                        (minutes - distance - 1) * v.flowRate);
            }
        }
    }

    class Valve {
        final String name;
        int flowRate;
        final Set<Valve> tunnels = new HashSet<>();
        final Map<Valve, Integer> distances = new HashMap<>();

        public Valve(String name) {
            this.name = name;
        }

        int computeFlow(List<Valve> openValves, int currentFlow, int minute, int timeLimit) {
            if (minute < 3) {
                log.info("Valve: {}, Open valves: {}, current flow: {}, minute: {}", this, openValves, currentFlow, minute);
            }
            // alternatives: wait, open valve or move
            int maximumFlow;
            // wait

            if (openValves.size() == valves.size()) {
                // all valves are open, no need to do anything, return directly
                //log.info ("All valves are open, returning: {}", currentFlow * (30 - minute));
                return currentFlow * (timeLimit - minute);
            }

            if (minute == timeLimit) {
                return currentFlow;
            } else {
                minute++;
            }

            if (openValves.contains(this)) {
                currentFlow += flowRate;
            }

            // wait
            //maximumFlow = computeFlow(new ArrayList<>(openValves), currentFlow, minute);
            maximumFlow = currentFlow;

            // move
            for (Valve v : tunnels) {
                maximumFlow = Math.max(v.computeFlow(new ArrayList<>(openValves), currentFlow, minute, timeLimit), maximumFlow);
            }

            // open valve if not opened
            if (!openValves.contains(this)) {
                openValves.add(this);
                maximumFlow = Math.max(computeFlow(new ArrayList<>(openValves), currentFlow, minute, timeLimit), maximumFlow);
            }

            // return maximum accumulated flow
            return currentFlow + maximumFlow;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
