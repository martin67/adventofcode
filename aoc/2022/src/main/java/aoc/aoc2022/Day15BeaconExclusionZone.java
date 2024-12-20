package aoc.aoc2022;

import aoc.common.Position;
import com.google.common.collect.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

@SuppressWarnings("UnstableApiUsage")
@Slf4j
public class Day15BeaconExclusionZone {
    final Map<Position, Sensor> sensors = new HashMap<>();
    final Set<Position> beacons = new HashSet<>();

    Day15BeaconExclusionZone(List<String> inputLines) {
        var pattern = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");

        for (String line : inputLines) {
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                Sensor sensor = new Sensor(new Position(Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2))));
                Position beacon = new Position(Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4)));
                sensor.closestBeacon = beacon;
                sensors.put(sensor.position, sensor);
                beacons.add(beacon);
            }
        }
    }

    int problem1(int row) {
        var intervals = getIntegerRangeSet(row);

        int size = 0;
        for (var range : intervals.asRanges()) {
            size += ContiguousSet.create(range, DiscreteDomain.integers()).size();
        }
        return size;
    }

    @NotNull
    RangeSet<Integer> getIntegerRangeSet(int row) {
        RangeSet<Integer> intervals = TreeRangeSet.create();

        for (Sensor sensor : sensors.values()) {
            // check if sensor covers row
            int yDistance = Math.abs(row - sensor.position.getY());
            int xStart = sensor.position.getX() - (sensor.distanceToClosestBeacon() - yDistance);
            int xEnd = sensor.position.getX() + (sensor.distanceToClosestBeacon() - yDistance);

            if (xStart <= xEnd) {
                intervals.add(Range.closed(xStart, xEnd));
            }
        }
        // remove any beacons from the locations
        for (Position beacon : beacons) {
            if (beacon.getY() == row) {
                intervals.remove(Range.closed(beacon.getX(), beacon.getX()));
            }
        }
        return intervals;
    }

    long problem2(int searchArea) {

        for (int i = 0; i < searchArea; i++) {
            RangeSet<Integer> intervals = getIntegerRangeSet(i);
            RangeSet<Integer> test = intervals.complement();
            if (test.asRanges().size() == 3) {
                log.info("Found");
                for (var r : test.asRanges()) {
                    if (r.hasLowerBound() && r.hasUpperBound() &&
                            r.upperEndpoint() - r.lowerEndpoint() == 2) {
                        log.info("r: {} ", r);
                        int x = r.upperEndpoint() - 1;
                        return x * 4000000L + i;
                    }
                }
            }
        }
        return -1;
    }

    static class Sensor {
        final Position position;
        Position closestBeacon;

        public Sensor(Position position) {
            this.position = position;
        }

        int distanceToClosestBeacon() {
            return position.distance(closestBeacon);
        }
    }
}
