package aoc.aoc2023;

import com.google.common.collect.Range;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day5IfYouGiveASeedAFertilizer {

    List<SeedMap> seedMaps = new ArrayList<>();
    List<Long> seeds = new ArrayList<>();
    List<Range<Long>> seedRanges = new ArrayList<>();

    public Day5IfYouGiveASeedAFertilizer(List<String> inputLines) {
        var pattern = Pattern.compile("(\\w+)-to-(\\w+) map:");
        Matcher matcher;

        SeedMap seedMap = null;
        for (String line : inputLines) {
            long start = -1;
            if (line.startsWith("seeds:")) {
                for (String s : line.substring(7).split(" ")) {
                    seeds.add(Long.parseLong(s));
                    if (start == -1) {
                        start = Long.parseLong(s);
                    } else {
                        seedRanges.add(Range.closed(start, start + Long.parseLong(s) - 1));
                        start = -1;
                    }
                }
            } else {
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    seedMap = new SeedMap(matcher.group(1), matcher.group(2));
                    seedMaps.add(seedMap);
                } else if (line.isBlank()) {
                    seedMap = null;
                } else {
                    String[] s = line.split(" ");
                    assert seedMap != null;
                    seedMap.mapLines.add(new MapLine(Long.parseLong(s[0]), Long.parseLong(s[1]), Long.parseLong(s[2])));
                }
            }
        }
    }

    public long problem1() {
        long lowestLocation = -1;
        for (long seed : seeds) {
            String source = "seed";
            long value = seed;
            while (!source.equals("location")) {
                String finalSource = source;
                var seedMap = seedMaps.stream()
                        .filter(sm -> sm.source.equals(finalSource))
                        .findFirst().orElseThrow();
                value = seedMap.map(value);
                source = seedMap.destination;
            }
            if (lowestLocation == -1) {
                lowestLocation = value;
            } else {
                lowestLocation = Math.min(value, lowestLocation);
            }
        }
        return lowestLocation;
    }

    public long problem2() {
        long totalLow = Long.MAX_VALUE;
        for (var seedRange : seedRanges) {
            log.info("### Evaluating: {}", seedRange);
            String source = "seed";
            var ranges = Set.of(seedRange);
            while (!source.equals("location")) {
                String finalSource = source;
                var seedMap = seedMaps.stream()
                        .filter(sm -> sm.source.equals(finalSource))
                        .findFirst().orElseThrow();
                var nextRanges = seedMap.map(ranges);
                log.debug("{} ({}) -> {} ({})", source, ranges.size(), seedMap.destination, nextRanges.size());
                ranges = nextRanges;
                source = seedMap.destination;
            }
            long lowestValue = ranges.stream().mapToLong(Range::lowerEndpoint).min().orElseThrow();
            log.debug("Lowest value: {}", lowestValue);
            totalLow = Math.min(lowestValue, totalLow);
        }
        return totalLow;
    }

    @Data
    static class SeedMap {
        String source;
        String destination;
        List<MapLine> mapLines = new ArrayList<>();

        public SeedMap(String source, String destination) {
            this.source = source;
            this.destination = destination;
        }

        long map(long in) {
            for (var mapLine : mapLines) {
                if (mapLine.source.contains(in)) {
                    return mapLine.destination.lowerEndpoint() + in - mapLine.source.lowerEndpoint();
                }
            }
            return in;
        }

        Set<Range<Long>> map(Set<Range<Long>> inList) {
            Collections.sort(mapLines);
            Set<Range<Long>> out = new HashSet<>();
            Queue<Range<Long>> inListQ = new LinkedList<>(inList);
            while (!inListQ.isEmpty()) {
                var in = inListQ.poll();
                boolean foundMatch = false;
                for (var mapLine : mapLines) {
                    if (in.isConnected(mapLine.source)) {
                        foundMatch = true;
                        var intersection = in.intersection(mapLine.source);
                        long offset = intersection.lowerEndpoint() - mapLine.source.lowerEndpoint();
                        long length = intersection.upperEndpoint() - intersection.lowerEndpoint();
                        var range = Range.closed(mapLine.destination.lowerEndpoint() + offset,
                                mapLine.destination.lowerEndpoint() + offset + length);
                        out.add(range);

                        if (in.lowerEndpoint() < intersection.lowerEndpoint()) {
                            var lower = Range.closed(in.lowerEndpoint(), intersection.lowerEndpoint() - 1);
                            inListQ.add(lower);
                        }
                        if (in.upperEndpoint() > intersection.upperEndpoint()) {
                            var upper = Range.closed(intersection.upperEndpoint() + 1, in.upperEndpoint());
                            inListQ.add(upper);
                        }
                    }
                }
                if (!foundMatch) {
                    out.add(in);
                }
            }

            return out;
        }
    }

    @Data
    static class MapLine implements Comparable<MapLine> {
        Range<Long> source;
        Range<Long> destination;

        public MapLine(long destinationStart, long sourceStart, long length) {
            source = Range.closed(sourceStart, sourceStart + length - 1);
            destination = Range.closed(destinationStart, destinationStart + length - 1);
        }

        @Override
        public int compareTo(@NotNull Day5IfYouGiveASeedAFertilizer.MapLine o) {
            return source.lowerEndpoint().compareTo(o.source.lowerEndpoint());
        }
    }
}