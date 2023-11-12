package aoc.aoc2021;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day12PassagePathing {

    private final Map<String, Cave> caves = new HashMap<>();

    public Day12PassagePathing(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s = line.split("-");
            String start = s[0];
            String end = s[1];

            Cave startCave = caves.computeIfAbsent(start, c -> new Cave(start));
            Cave endCave = caves.computeIfAbsent(end, c -> new Cave(end));
            startCave.adjacent.add(endCave);
            endCave.adjacent.add(startCave);
        }
    }

    public int problem1() {
        return cavePath(caves.get("start"), new ArrayDeque<>());
    }

    private int cavePath(Cave c, Deque<Cave> explored) {
        int reachedEnd = 0;
        log.debug("Exploring cave {}", c);
        explored.push(c);

        for (Cave adj : c.adjacent) {
            log.debug("Checking {}", c);
            if (adj.name.equals("start")) {
                log.debug("Don't go back to start");
            } else if (adj.name.equals("end")) {
                log.debug("Found end, {}", explored);
                reachedEnd += 1;
            } else if (adj.isSmall() && explored.contains(adj)) {
                log.debug("Already visited {}, skipping", adj.name);
            } else {
                reachedEnd += cavePath(adj, new ArrayDeque<>(explored));
            }
        }
        log.debug("Returning from cave {}, score {}", c, reachedEnd);
        return reachedEnd;
    }

    public int problem2() {
        Set<Cave> smallCaves = caves.values().stream()
                .filter(Cave::isSmall)
                .filter(c -> !c.name.equals("start"))
                .filter(c -> !c.name.equals("end"))
                .collect(Collectors.toSet());
        Set<String> allPaths = new HashSet<>();

        for (Cave smallCave : smallCaves) {
            cavePath2(caves.get("start"), new ArrayDeque<>(), allPaths, smallCave);
        }
        return allPaths.size();
    }

    private void cavePath2(Cave cave, Deque<Cave> explored, Set<String> foundPaths, Cave smallCave) {
        log.debug("Exploring cave {}, smallCave: {}", cave, smallCave);
        explored.push(cave);

        for (Cave adj : cave.adjacent) {
            log.debug("Checking {} from {}", adj, cave);
            if (adj.name.equals("start")) {
                log.debug("Don't go back to start");
            } else if (adj.name.equals("end")) {
                log.debug("####### Found end ({}),  {}", smallCave, printPath(explored));
                foundPaths.add(printPath(explored));
            } else if (adj.isSmall() && adj == smallCave && explored.stream().filter(c -> c == smallCave).count() == 1) {
                log.debug("Checking {} a second time", adj);
                cavePath2(adj, new ArrayDeque<>(explored), foundPaths, smallCave);
            } else if (adj.isSmall() && explored.contains(adj)) {
                log.debug("Already visited {}, skipping", adj.name);
            } else {
                cavePath2(adj, new ArrayDeque<>(explored), foundPaths, smallCave);
            }
        }
        log.debug("Returning from cave {}", cave);
    }

    private String printPath(Deque<Cave> explored) {
        Iterator<Cave> it = explored.descendingIterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next().name).append(",");
        }
        sb.append("end");
        return sb.toString();
    }

    static class Cave {
        final String name;
        final Set<Cave> adjacent = new HashSet<>();

        public Cave(String name) {
            this.name = name;
        }

        boolean isBig() {
            return name.equals(name.toUpperCase());
        }

        boolean isSmall() {
            return !isBig();
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
