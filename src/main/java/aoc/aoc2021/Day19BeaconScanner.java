package aoc.aoc2021;

import aoc.SpacePosition;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day19BeaconScanner {

    final List<BeaconScanner> scanners = new ArrayList<>();

    public Day19BeaconScanner(List<String> inputLines) {
        Pattern scannerPattern = Pattern.compile("--- scanner (\\d+) ---");
        Pattern beaconPattern = Pattern.compile("(-?\\d+),(-?\\d+),(-?\\d+)");

        BeaconScanner scanner = null;
        for (String line : inputLines) {
            Matcher matcher = scannerPattern.matcher(line);
            if (matcher.find()) {
                scanner = new BeaconScanner(Integer.parseInt(matcher.group(1)));
                scanners.add(scanner);
            }
            matcher = beaconPattern.matcher(line);
            if (matcher.find()) {
                scanner.beacons.add(new SpacePosition(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3))));
            }
        }
    }

    int problem1() {
        // Compute all 24 different views
        for (BeaconScanner scanner : scanners) {
            Set<Set<SpacePosition>> allViews = new HashSet<>();
            Set<SpacePosition> setB = new HashSet<>();
            Set<SpacePosition> setC = new HashSet<>();
            // one
            for (int x : List.of(1, -1)) {
                for (int y : List.of(1, -1)) {
                    for (int z : List.of(1, -1)) {
                        Set<SpacePosition> setA = new HashSet<>();
                        for (SpacePosition spacePosition : scanner.beacons) {
                            setA.add(new SpacePosition(spacePosition.getX() * x, spacePosition.getY() * y, spacePosition.getZ() * z));
                            setA.add(new SpacePosition(spacePosition.getY() * y, spacePosition.getZ() * z, spacePosition.getX() * x));
                            setA.add(new SpacePosition(spacePosition.getZ() * z, spacePosition.getX() * x, spacePosition.getY() * y));
                        }
                        allViews.add(setA);
                    }
                }
            }
            //allViews.add(setB);
            //allViews.add(setC);
        }
        return 0;
    }


    int problem1b() {

        // for each beacon, compute the distance to all other beacons
        for (BeaconScanner scanner : scanners) {
            for (SpacePosition src : scanner.beacons) {
                for (SpacePosition dst : scanner.beacons) {
                    if (src != dst) {
                        scanner.distances.computeIfAbsent(src, p -> new HashMap<>());
                        scanner.distances.get(src).put(dst, src.distance(dst));
                    }
                }
            }
        }

        // Compare distances between scanners
        for (BeaconScanner src : scanners) {
            for (BeaconScanner dst : scanners) {
                if (src != dst) {
                    Map<SpacePosition, SpacePosition> mappings = new HashMap<>();
                    for (SpacePosition srcPos : src.beacons) {
                        for (SpacePosition dstPos : dst.beacons) {
                            Map<Integer, Integer> numberFrequency = new HashMap<>();
                            for (int v : src.distances.get(srcPos).values()) {
                                numberFrequency.merge(v, 1, Integer::sum);
                            }
                            for (int v : dst.distances.get(dstPos).values()) {
                                numberFrequency.merge(v, 1, Integer::sum);
                            }
                            long equals = numberFrequency.values().stream()
                                    .filter(v -> v > 1)
                                    .mapToInt(v -> v)
                                    .sum() / 2;
                            if (equals >= 11) {
                                //log.info("scanner {} - {} vs scanner {} - {} , Number of equals: {}", src.id, srcPos, dst.id, dstPos, equals);
                                log.info("scanner {} vs scanner {}, Number of equals: {}", src.id, dst.id, equals);
                                mappings.put(srcPos, dstPos);
                            }
                        }
                        // compute offset vs scanner 0
                        //log.info("")

                    }
                    if (mappings.size() >= 12) {
                        log.info("Computing offset between scanner {} and scanner {}", src.id, dst.id);
                        Set<Integer> possibleX1 = new HashSet<>();
                        Set<Integer> possibleX2 = new HashSet<>();
                        Set<Integer> possibleY1 = new HashSet<>();
                        Set<Integer> possibleY2 = new HashSet<>();
                        Set<Integer> possibleZ1 = new HashSet<>();
                        Set<Integer> possibleZ2 = new HashSet<>();
                        for (Map.Entry<SpacePosition, SpacePosition> pos : mappings.entrySet()) {
                            int x1 = Math.abs(pos.getKey().getX() - pos.getValue().getX());
                            possibleX1.add(x1);
                            int x2 = pos.getKey().getX() + pos.getValue().getX();
                            possibleX2.add(x2);
                            int y1 = Math.abs(pos.getKey().getY() - pos.getValue().getY());
                            possibleY1.add(y1);
                            int y2 = pos.getKey().getY() + pos.getValue().getY();
                            possibleY2.add(y2);
                            int z1 = Math.abs(pos.getKey().getZ() - pos.getValue().getZ());
                            possibleZ1.add(z1);
                            int z2 = pos.getKey().getZ() + pos.getValue().getZ();
                            possibleZ2.add(z2);
                            log.info("src: {}, dst: {}, possible values x1: {}, x2: {}, y1: {}, y2: {}, z1: {}, z2: {}",
                                    pos.getKey(), pos.getValue(), x1, x2, y1, y2, z1, z2);

                        }

                    }
                }
            }
        }
        return 0;
    }

    int problem2() {
        return 0;
    }

    static class BeaconScanner {
        final int id;
        final Set<SpacePosition> beacons = new HashSet<>();
        final Map<SpacePosition, Map<SpacePosition, Integer>> distances = new HashMap<>();
        SpacePosition offset = new SpacePosition(0, 0, 0);

        public BeaconScanner(int id) {
            this.id = id;
        }

        List<Integer> sortedDistances(SpacePosition pos) {
            Collection<Integer> a = distances.get(pos).values();
            List<Integer> out = new ArrayList<>(a);
            Collections.sort(out);
            return out;
        }
    }
}
