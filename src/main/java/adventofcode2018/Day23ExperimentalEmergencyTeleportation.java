package adventofcode2018;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jgrapht.Graph;
import org.jgrapht.alg.clique.DegeneracyBronKerboschCliqueFinder;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day23ExperimentalEmergencyTeleportation {

    @Data
    @AllArgsConstructor
    static class Nanobot {
        SpacePosition pos;
        Long r;

        boolean overlapping(Nanobot n) {
            // true if then nanobot overlaps n
            // distance between n1 and n2
            // 1...r...r......2
            // distance 1 -2 : 15
            // r1 = 4, r2 = 7. 15 > 4+7
            return pos.distance(n.pos) <= (r + n.r);
        }

        @Override
        public String toString() {
            return "(" + pos.x + "," + pos.y + "," + pos.z + ")/" + r;
        }
    }

    @Data
    static class Range {
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;

        void add(long value) {
            if (value > max) {
                max = value;
            }
            if (value < min) {
                min = value;
            }
        }

        long size() {
            return max - min;
        }

        long middle() {
            return min + (max - min) / 2;
        }
    }

    private Set<Nanobot> nanobots = new HashSet<>();
    private Range xRange = new Range();
    private Range yRange = new Range();
    private Range zRange = new Range();
    private Graph<Nanobot, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);


    public Day23ExperimentalEmergencyTeleportation(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        Pattern pattern = Pattern.compile("^pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)$");

        for (String row : inputStrings) {
            Matcher matcher = pattern.matcher(row);
            if (matcher.find()) {
                long x = Long.parseLong(matcher.group(1));
                long y = Long.parseLong(matcher.group(2));
                long z = Long.parseLong(matcher.group(3));
                long r = Long.parseLong(matcher.group(4));
                nanobots.add(new Nanobot(new SpacePosition(x, y, z), r));
                xRange.add(x);
                yRange.add(y);
                zRange.add(z);
            }
        }
        System.out.printf("Read %d nanobots from %s\n", nanobots.size(), fileName);
        System.out.printf("X:  min: %d, max: %d, size: %d\n", xRange.min, xRange.max, xRange.size());
        System.out.printf("Y:  min: %d, max: %d, size: %d\n", yRange.min, yRange.max, yRange.size());
        System.out.printf("Z:  min: %d, max: %d, size: %d\n", zRange.min, zRange.max, zRange.size());
        System.out.printf("Number of coordinates: %d\n", xRange.size() * yRange.size() * zRange.size());
    }

    int nanobotsInRange() {
        Nanobot strongestNanobot = nanobots.stream().max(Comparator.comparing(Nanobot::getR)).get();

        System.out.printf("Strongest nanobot: %s\n", strongestNanobot);

        return nanobotsInRange(strongestNanobot).size();
    }

    private Set<Nanobot> nanobotsInRange(Nanobot nanoBot) {
        return nanobots.stream().filter(n -> n.pos.distance(nanoBot.pos) <= n.r).collect(Collectors.toSet());
    }

    private Long nanobotsInRange(SpacePosition sp) {
        return nanobots.stream().filter(n -> n.pos.distance(sp) <= n.r).count();
    }

    private Set<SpacePosition> positionsOnRadius(Nanobot nanobot) {

        // all positions where nanobot.distance(pos) == r
        Set<SpacePosition> onRadius = new HashSet<>();

        // Only add values that are inside the "box"
        for (long x = Math.max(xRange.min, nanobot.pos.x - nanobot.r); x < Math.min(xRange.max, nanobot.pos.x + nanobot.r) + 1; x++) {
            for (long y = Math.max(yRange.min, nanobot.pos.y - nanobot.r); y < Math.min(yRange.max, nanobot.pos.y + nanobot.r) + 1; y++) {
                for (long z = Math.max(zRange.min, nanobot.pos.z - nanobot.r); z < Math.min(zRange.max, nanobot.pos.z + nanobot.r) + 1; z++) {
                    SpacePosition pos = new SpacePosition(x, y, z);
                    if (nanobot.pos.distance(pos) == nanobot.r) {
                        onRadius.add(pos);
                    }
                }
            }
        }
        System.out.printf("%d positions on radius for nanobot: %s\n", onRadius.size(), nanobot);
        return onRadius;
    }

    private SpacePosition findCenterPosition(Set<Nanobot> nanobotSet) {

        SpacePosition currentPos = new SpacePosition(xRange.middle(), yRange.middle(), zRange.middle());
        //nu.hagelin.adventofcode.cal2018.SpacePosition currentPos = new nu.hagelin.adventofcode.cal2018.SpacePosition(0, 0, 0);
        boolean quit = false;
        int offset = 1;

        // Find the adjacent point that has the shortest distance to all nanobots in the set
        while (!quit) {
            SpacePosition newPos = currentPos.adjacent(offset).stream()
                    .min(Comparator.comparing(sp -> distanceToNanobots(sp, nanobotSet))).get();
            if (distanceToNanobots(newPos, nanobotSet) < distanceToNanobots(currentPos, nanobotSet)) {
//                System.out.printf("Moving from %s (%d) to %s (%d) with offset %d\n",
//                        currentPos, distanceToNanobots(currentPos, nanobotSet),
//                        newPos, distanceToNanobots(newPos, nanobotSet), offset);
                currentPos = newPos;
            } else {

                // make the search offset smaller
                if (offset == 1) {
                    // Stop here
                    quit = true;
                } else {
                    offset = offset / 10;
                    // System.out.printf("Decreasing offset to %d\n", offset);
                }
            }
        }

        System.out.printf("Ending at %s (%d)\n", currentPos, distanceToNanobots(currentPos, nanobots));
        return currentPos;
    }

    private SpacePosition findMostNanobots(SpacePosition currentPos) {

        boolean quit = false;

        // Find the adjacent point that has the most nanobots in range
        while (!quit) {
            SpacePosition newPos = currentPos.adjacent().stream()
                    .max(Comparator.comparing(this::nanobotsInRange)).get();
            if (nanobotsInRange(newPos) > nanobotsInRange(currentPos)) {
//                System.out.printf("Moving from %s (%d) to %s (%d) with offset %d\n",
//                        currentPos, distanceToNanobots(currentPos, nanobotSet),
//                        newPos, distanceToNanobots(newPos, nanobotSet), offset);
                currentPos = newPos;
            } else {
                quit = true;
            }
        }
        System.out.printf("Ending at %s (%d)\n", currentPos, nanobotsInRange(currentPos));
        return currentPos;
    }

    private Long distanceToNanobots(SpacePosition sp, Set<Nanobot> nanobotSet) {
        long dist = 0;
        for (Nanobot nanobot : nanobotSet) {
            dist += sp.distance(nanobot.pos);
        }
        return dist;
    }

    long shortestDistance() {
        setupGraph();
        return findClosestNanobots();
    }

    private void setupGraph() {
        System.out.println("Setting up graph; adding vertices");
        nanobots.forEach(n -> graph.addVertex(n));

        System.out.println("Setting up graph; adding edges");
        for (Nanobot nanobot : nanobots) {
            for (Nanobot n2 : nanobots) {
                if (!nanobot.equals(n2) && nanobot.overlapping(n2)) {
                    graph.addEdge(nanobot, n2);
                }
            }
        }
        System.out.println("Number of vertexes: " + graph.vertexSet().size());
        System.out.println("Number of edges: " + graph.edgeSet().size());

//        graph.vertexSet().forEach(v -> {
//                    System.out.printf("Vertex: %s: ", v);
//                    System.out.printf("[Edges: %d]", graph.outgoingEdgesOf(v).size());
//                    graph.outgoingEdgesOf(v).forEach(e -> System.out.printf(" %s", e));
//                    System.out.println();
//                }
//        );

        System.out.println();
        //graph.edgeSet().forEach(e -> System.out.printf("Edge: %s\n", e));
    }

    private SpacePosition findBruteForceInCube(Set<Nanobot> closestNanobots) {
        // Brute force, does not work...
        SpacePosition target = null;
        long nanoBotsInRangeFromTarget = 0;
//        for (int x = Math.min(foundStart.pos.x, foundEnd.pos.x); x <= Math.max(foundStart.pos.x, foundEnd.pos.x); x++) {
//            for (int y = Math.min(foundStart.pos.y, foundEnd.pos.y); y <= Math.max(foundStart.pos.y, foundEnd.pos.y); y++) {
//                for (int z = Math.min(foundStart.pos.z, foundEnd.pos.z); z <= Math.max(foundStart.pos.z, foundEnd.pos.z); z++) {
//                    nu.hagelin.adventofcode.cal2018.SpacePosition sp = new nu.hagelin.adventofcode.cal2018.SpacePosition(x, y, z);
//                    long nanoBotsInRangeFromSp = nanobotsInRange(sp);
////                        System.out.printf("%s - %d\n", sp, nanobotsInRange(sp));
//                    if (nanoBotsInRangeFromSp > nanoBotsInRangeFromTarget) {
//                        target = sp;
//                        nanoBotsInRangeFromTarget = nanoBotsInRangeFromSp;
//                    }
//                }
//            }
//        }

        return target;
    }

    private SpacePosition findBruteForceInRadius(Set<Nanobot> closestNanobots) {
        // Find nanobot with smallest radius
        Nanobot smallestNanobot = closestNanobots.stream().min(Comparator.comparing(Nanobot::getR)).get();
        System.out.println("Smallest nanobot in clique: " + smallestNanobot);

        // Closest point must be in the smallest nanobot. Brute force search there
        // => brute force does not work with a big radius (
        // Number of nanobots in clique: 976
        // Smallest nanobot in clique: (76683931,61531531,50268698)/49787058
        SpacePosition target = null;
        long nanoBotsInRangeFromTarget = 0;
        long radiusSquare = (long) Math.pow(smallestNanobot.r, 2);
        for (long x = smallestNanobot.pos.x - smallestNanobot.r; x < smallestNanobot.pos.x + smallestNanobot.r + 1; x++) {
            for (long y = smallestNanobot.pos.y - smallestNanobot.r; y < smallestNanobot.pos.y + smallestNanobot.r + 1; y++) {
                for (long z = smallestNanobot.pos.z - smallestNanobot.r; z < smallestNanobot.pos.z + smallestNanobot.r + 1; z++) {
                    if (Math.pow(x - smallestNanobot.pos.x, 2) + Math.pow(y - smallestNanobot.pos.y, 2) + Math.pow(z - smallestNanobot.pos.z, 2) <= radiusSquare) {
                        SpacePosition sp = new SpacePosition(x, y, z);
                        long nanoBotsInRangeFromSp = nanobotsInRange(sp);
//                        System.out.printf("%s - %d\n", sp, nanobotsInRange(sp));
                        if (nanoBotsInRangeFromSp > nanoBotsInRangeFromTarget) {
                            target = sp;
                            nanoBotsInRangeFromTarget = nanoBotsInRangeFromSp;
                        }
                    }
                }
            }
        }
        return target;
    }

    private SpacePosition findBruteForceManhattanTunnel(Set<Nanobot> closestNanobots) {

        // Find the two closest nanobots and brute force in the "manhattan" tunnel between the two
        System.out.println("Finding the two closest nanobots");
        long distance = Long.MAX_VALUE;
        Nanobot foundStart = null;
        Nanobot foundEnd = null;
        for (Nanobot start : nanobots) {
            for (Nanobot end : nanobots) {
                if (start != end && start.pos.distance(end.pos) < distance) {
                    foundStart = start;
                    foundEnd = end;
                    distance = start.pos.distance(end.pos);
                }
            }
        }
        System.out.printf("Closest nanobots: %s - %s, distance %d\n", foundStart, foundEnd, distance);

        // go from start to end, finding the node that has the shortest distance and check all adjacent nodes for
        // the largest number of nanobots in range. The target should be on the straight line between start and end
        SpacePosition target = null;
        long nanoBotsInRangeFromTarget = 0;
        SpacePosition currentPosition = foundStart.pos;
        while (!currentPosition.equals(foundEnd.pos)) {
            // check all neighbors for best cover
            SpacePosition found = currentPosition.adjacent().stream().max(Comparator.comparing(this::nanobotsInRange)).get();
            if (nanobotsInRange(found) > nanoBotsInRangeFromTarget) {
                target = found;
                nanoBotsInRangeFromTarget = nanobotsInRange(found);
            }
            // move to next position
            Nanobot finalFoundEnd = foundEnd;
            currentPosition = currentPosition.adjacent().stream().min(Comparator.comparing(sp -> (int) sp.distance(finalFoundEnd.pos))).get();
        }

        return target;
    }

    private long findClosestNanobots() {
        System.out.println("Finding cliques");
        //BronKerboschCliqueFinder<Nanobot, DefaultEdge> bronKerboschCliqueFinder = new BronKerboschCliqueFinder<>(graph);
        //PivotBronKerboschCliqueFinder<Nanobot, DefaultEdge> bronKerboschCliqueFinder = new PivotBronKerboschCliqueFinder<>(graph);
        DegeneracyBronKerboschCliqueFinder<Nanobot, DefaultEdge> bronKerboschCliqueFinder = new DegeneracyBronKerboschCliqueFinder<>(graph);

        System.out.println("Iterating 1");
        Iterator<Set<Nanobot>> iterator = bronKerboschCliqueFinder.maximumIterator();
        System.out.println("Iterating 2");
        long longest = 0;
        while (iterator.hasNext()) {
            Set<Nanobot> closestNanobots = iterator.next();
            System.out.printf("Number of nanobots in maximum clique: %d\n", closestNanobots.size());

            // target = findBruteForceManhattanTunnel(closestNanobots);
            // target = findBruteForceInRadius(closestNanobots);
            SpacePosition origin = new SpacePosition(0, 0, 0);

            // This was the tricky part!!!
            // the position is not the interesting part, only the distance
            for (Nanobot n : closestNanobots) {
                long dist = n.pos.distance(origin) - n.r;
                if (dist > longest) {
                    longest = dist;
                }
            }
        }
        return longest;
    }
}
