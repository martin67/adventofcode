package aoc.aoc2015;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day19MedicineForRudolph {

    private final Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    Map<String, Set<String>> molecules = new HashMap<>();
    Map<String, String> moleculesReverse = new HashMap<>();
    Set<String> startPoints = new HashSet<>();

    Set<String> replacements = new HashSet<>();
    String outcome;


    public Day19MedicineForRudolph(List<String> inputLines) {
        Pattern replacementPattern = Pattern.compile("^(\\w+) => (\\w+)$");
        Pattern outcomePattern = Pattern.compile("^(\\w+)$");
        Matcher matcher;

        for (String line : inputLines) {
            matcher = replacementPattern.matcher(line);
            if (matcher.find()) {
                String in = matcher.group(1);
                String out = matcher.group(2);
                if (!molecules.containsKey(in)) {
                    molecules.put(in, new HashSet<>());
                }
                molecules.get(in).add(out);
//                if (in.equals("e")) {
//                    startPoints.add(out);
//                } else {
//                    moleculesReverse.put(out, in);
//                }
                moleculesReverse.put(out, in);
            }
            matcher = outcomePattern.matcher(line);
            if (matcher.find()) {
                outcome = matcher.group(1);
            }
        }
    }

    int distinctMolecules() {
        return possibleMoleculesFrom(outcome).size();
    }

    Set<String> possibleMoleculesFrom(String parent) {
        int index = 0;
        Set<String> possibleMolecules = new HashSet<>();

        while (index < parent.length()) {
            if (molecules.containsKey(parent.substring(index, index + 1))) {
                for (String replacement : molecules.get(parent.substring(index, index + 1))) {
                    possibleMolecules.add(parent.substring(0, index) + replacement + parent.substring(index + 1));
                }
            } else if (index < parent.length() - 1 && molecules.containsKey(parent.substring(index, index + 2))) {
                for (String replacement : molecules.get(parent.substring(index, index + 2))) {
                    possibleMolecules.add(parent.substring(0, index) + replacement + parent.substring(index + 2));
                }
                index++;
            }
            index++;
        }
        return possibleMolecules;
    }

    Set<String> possibleMoleculesFrom2(String parent) {
        int index = 0;
        Set<String> possibleMolecules = new HashSet<>();

        while (index < parent.length()) {
            // Check all different lengths
            for (int length = 1; length < parent.length() - index + 1; length++) {
                String searchString = parent.substring(index, index + length);
                if (moleculesReverse.containsKey(searchString)) {
                    possibleMolecules.add(parent.substring(0, index) + moleculesReverse.get(searchString) + parent.substring(index + searchString.length()));
                }
            }
            index++;
        }
        // only return the shortest molecule to cut down on options
//        Comparator<String> byLength = (e1, e2) -> e1.length() > e2.length() ? -1 : 1;
//        Optional<String> shortestMolecules = possibleMolecules.stream().sorted(byLength.reversed()).findFirst();
//        Set<String> answer = new HashSet<>();
//        if (shortestMolecules.isPresent()) {
//            answer.add(shortestMolecules.get());
//        }
//        return answer;

        return possibleMolecules;
    }

    Set<String> possibleMoleculesFromBackwards(String parent) {
        int index = 0;
        Set<String> possibleMolecules = new HashSet<>();

        while (index < parent.length()) {
            if (molecules.containsKey(parent.substring(index, index + 1))) {
                for (String replacement : molecules.get(parent.substring(index, index + 1))) {
                    possibleMolecules.add(parent.substring(0, index) + replacement + parent.substring(index + 1));
                }
            } else if (index < parent.length() - 1 && molecules.containsKey(parent.substring(index, index + 2))) {
                for (String replacement : molecules.get(parent.substring(index, index + 2))) {
                    possibleMolecules.add(parent.substring(0, index) + replacement + parent.substring(index + 2));
                }
                index++;
            }
            index++;
        }
        return possibleMolecules;
    }

    int fewestSteps() {
        Queue<String> moleculesToAdd = new LinkedList<>();
        Set<String> moleculesProcessed = new HashSet<>();

        String start = "e";
        moleculesToAdd.add(start);
        graph.addVertex(start);

        while (!moleculesToAdd.isEmpty()) {
            log.info("Queue size: {}", moleculesToAdd.size());
            String parent = moleculesToAdd.poll();

            // compute all possible molecules one step from parent
            Set<String> possibleMolecules = possibleMoleculesFrom(parent);
            //log.info("{} possible molecules from {}", possibleMolecules.size(), parent);

            for (String child : possibleMolecules) {
                // Only molecules that are equal or less to the outcome is interesting
                if (child.length() <= outcome.length()) {
                    // Create the vertex if it doesn't exist
                    if (!graph.containsVertex(child)) {
                        graph.addVertex(child);
                        // also add the new child to molecules to scan
                        moleculesToAdd.add(child);
                    }

                    // Create an edge between the parent and child
                    graph.addEdge(parent, child);
                    log.info("Vertices: {}, edges {}", graph.vertexSet().size(), graph.edgeSet().size());
                }
            }
        }

        log.info("Computing shortest path between {} and {}", start, outcome);
        // Compute the shortest path between start (e) and end (outcome)
        DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths(start);
        return iPaths.getPath(outcome).getLength();
    }

    int fewestSteps2() {
        Queue<String> moleculesToAdd = new LinkedList<>();

        // Remove the "e"
//        moleculesReverse.

        // Go backwards instead
        String start = outcome;
        //String end = "e";
        moleculesToAdd.add(start);
        graph.addVertex(start);

        while (!moleculesToAdd.isEmpty()) {
            log.info("Queue size: {}", moleculesToAdd.size());
            String parent = moleculesToAdd.poll();

            // compute all possible molecules one step from parent
            Set<String> possibleMolecules = possibleMoleculesFrom2(parent);
            //log.info("{} possible molecules from {}", possibleMolecules.size(), parent);

            for (String child : possibleMolecules) {
                // Create the vertex if it doesn't exist
                if (!graph.containsVertex(child)) {
                    graph.addVertex(child);
                    // also add the new child to molecules to scan
                    moleculesToAdd.add(child);
                }

                // Create an edge between the parent and child
                graph.addEdge(parent, child);
                log.info("Vertices: {}, edges {}", graph.vertexSet().size(), graph.edgeSet().size());
            }
        }

        int shortestPath = Integer.MAX_VALUE;
        for (String end : startPoints) {
            log.info("Computing shortest path between {} and {}", start, end);
            // Compute the shortest path between start (e) and end (outcome)
            DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
            ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths(start);
            //return iPaths.getPath(end).getLength();
            if (iPaths.getPath(end) != null) {
                int pathLength = iPaths.getPath(end).getLength();
                log.info("Length: {}", pathLength);
                if (pathLength < shortestPath) {
                    shortestPath = pathLength;
                }
            } else {
                log.info("No path found");
            }
        }
        return shortestPath + 1;
    }

    int fewestSteps3() {
        // reduce the outcome with the largest molecules
        //moleculesReverse
        int count = 0;
        String result = outcome;
        while (!result.equals("e")) {
            result = reduceMolecule(result);
            count++;
        }
        return count;
    }

    String reduceMolecule(String input) {
        List<String> sorted = moleculesReverse.keySet().stream().sorted(Comparator.comparingInt(String::length).reversed()).collect(Collectors.toList());
        boolean found = false;
        int index = 0;
        String result = null;

        while (!found) {

            String searchTerm = sorted.get(index);
            if (input.contains(searchTerm)) {
                result = input.replaceFirst(searchTerm, moleculesReverse.get(searchTerm));
                found = true;
            } else {
                index++;
            }
        }
        log.info("Reducing {} to {}", input, result);
        return result;
    }
}