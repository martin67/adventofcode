import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day23ExperimentalEmergencyTeleportation {

    @Data
    @AllArgsConstructor
    static class Nanobot {
        SpacePosition pos;
        int r;

        @Override
        public String toString() {
            return "{(x=" + pos.x + ", y=" + pos.y + ", z=" + pos.z + "), r=" + r + "}";
        }
    }

    @Data
    static class Range {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        void add(int value) {
            if (value > max) {
                max = value;
            }
            if (value < min) {
                min = value;
            }
        }

        int size() {
            return max - min;
        }

        int middle() {
            return min + (max - min) / 2;
        }
    }

    private Set<Nanobot> nanobots = new HashSet<>();
    private Range xRange = new Range();
    private Range yRange = new Range();
    private Range zRange = new Range();

    public Day23ExperimentalEmergencyTeleportation(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        Pattern pattern = Pattern.compile("^pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)$");

        for (String row : inputStrings) {
            Matcher matcher = pattern.matcher(row);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                int z = Integer.parseInt(matcher.group(3));
                int r = Integer.parseInt(matcher.group(4));
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

    int nanoBotsInRange() {
        Nanobot strongestNanobot = nanobots.stream().max(Comparator.comparing(Nanobot::getR)).get();

        System.out.printf("Strongest nanobot: %s\n", strongestNanobot);

        Set<Nanobot> inRange = nanoBotsInRange(strongestNanobot);

        return inRange.size();
    }

    private Set<Nanobot> nanoBotsInRange(Nanobot nanoBot) {
        return nanobots.stream().filter(n -> n.pos.distance(nanoBot.pos) <= nanoBot.r).collect(Collectors.toSet());
    }

    private Set<SpacePosition> positionsOnRadius(Nanobot nanobot) {

        // all positions where nanobot.distance(pos) == r
        Set<SpacePosition> onRadius = new HashSet<>();

        // Only add values that are inside the "box"
        for (int x = Math.max(xRange.min, nanobot.pos.x - nanobot.r); x < Math.min(xRange.max, nanobot.pos.x + nanobot.r) + 1; x++) {
            for (int y = Math.max(yRange.min, nanobot.pos.y - nanobot.r); y < Math.min(yRange.max, nanobot.pos.y + nanobot.r) + 1; y++) {
                for (int z = Math.max(zRange.min, nanobot.pos.z - nanobot.r); z < Math.min(zRange.max, nanobot.pos.z + nanobot.r) + 1; z++) {
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

    long shortestDistance() {

        SpacePosition currentPos = new SpacePosition(xRange.middle(), yRange.middle(), zRange.middle());
        //SpacePosition currentPos = new SpacePosition(0, 0, 0);
        boolean quit = false;
        int offset = 1000000;

        // Find the adjacent point that has the shortest distance to all nanobots
        while (!quit) {
            SpacePosition newPos = currentPos.adjacent(offset).stream().min(Comparator.comparing(this::distanceToAllNanobots)).get();
            if (distanceToAllNanobots(newPos) < distanceToAllNanobots(currentPos)) {
                System.out.printf("Moving from %s (%d) to %s (%d) with offset\n",
                        currentPos, distanceToAllNanobots(currentPos),
                        newPos, distanceToAllNanobots(newPos), offset);
                currentPos = newPos;
            } else {

                // make the search offset smaller
                if (offset == 1) {
                    // Stop here
                    quit = true;
                } else {
                    offset = offset / 10;
                    System.out.printf("Decreasing offset to %d\n", offset);
                }
            }
        }

        System.out.printf("Ending at %s (%d)\n", currentPos, distanceToAllNanobots(currentPos));
        return currentPos.distance(new SpacePosition(0, 0, 0));
    }

    private long distanceToAllNanobots(SpacePosition sp) {
        long dist = 0;
        for (Nanobot nanobot : nanobots) {
            dist += Math.abs(sp.distance(nanobot.pos) - nanobot.r);
        }
        return dist;
    }
}

// 114918899 -- too low...
// 138697264 -- too low...