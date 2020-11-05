package aoc.aoc2015;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day19MedicineForRudolph {

    private final Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    Map<String, Set<String>> molecules = new HashMap<>();
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
            }
            matcher = outcomePattern.matcher(line);
            if (matcher.find()) {
                outcome = matcher.group(1);
            }
        }
    }

    int distinctMolecules() {
        int index = 0;
        while (index < outcome.length()) {
            if (molecules.containsKey(outcome.substring(index, index + 1))) {
                for (String replacement : molecules.get(outcome.substring(index, index + 1))) {
                    replacements.add(outcome.substring(0, index) + replacement + outcome.substring(index + 1));
                }
            } else if (index < outcome.length() - 1 && molecules.containsKey(outcome.substring(index, index + 2))) {
                for (String replacement : molecules.get(outcome.substring(index, index + 2))) {
                    replacements.add(outcome.substring(0, index) + replacement + outcome.substring(index + 2));
                }
                index++;
            }
            index++;
        }
        return replacements.size();
    }

    int fewestSteps() {
        Queue<String> moleculesToAdd = new LinkedList<>();
        moleculesToAdd.add("e");
        graph.addVertex("e");

        while (!moleculesToAdd.isEmpty()) {
            String parent = moleculesToAdd.poll();
            if (molecules.containsKey(parent)) {
                for (String child : molecules.get(parent)) {
                    graph.addVertex(child);
                    graph.addEdge(parent, child);
                    moleculesToAdd.add(child);
                }
            }
        }
        Map<String, Integer> distanceToE = new HashMap<>();

        BreadthFirstIterator<String, DefaultEdge> it = new BreadthFirstIterator<>(graph, "e");
        while (it.hasNext()) {
            String molecule = it.next();
            int depth = it.getDepth(molecule);
            log.info("{} :{}", molecule, depth);
        }
        return 0;
    }
}
