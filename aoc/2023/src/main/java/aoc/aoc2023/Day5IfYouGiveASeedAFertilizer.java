package aoc.aoc2023;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day5IfYouGiveASeedAFertilizer {

    List<SeedMap> seedMaps = new ArrayList<>();
    List<BigInteger> seeds = new ArrayList<>();

    public Day5IfYouGiveASeedAFertilizer(List<String> inputLines) {
        var pattern = Pattern.compile("(\\w+)-to-(\\w+) map:");
        Matcher matcher;

        SeedMap seedMap = null;
        for (String line : inputLines) {
            if (line.startsWith("seeds:")) {
                for (String s : line.substring(7).split(" ")) {
                    seeds.add(new BigInteger(s));
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
                    seedMap.mapLines.add(new MapLine(new BigInteger(s[0]), new BigInteger(s[1]), new BigInteger(s[2])));
                }
            }
        }
    }

    public String problem1() {
        BigInteger lowestLocation = null;
        for (BigInteger seed : seeds) {
            String source = "seed";
            BigInteger value = seed;
            while (!source.equals("location")) {
                String finalSource = source;
                SeedMap seedMap = seedMaps.stream()
                        .filter(sm -> sm.source.equals(finalSource))
                        .findFirst().orElseThrow();
                value = seedMap.map(value);
                source = seedMap.destination;
            }
            if (lowestLocation == null) {
                lowestLocation = value;
            } else {
                lowestLocation = value.min(lowestLocation);
            }
        }
        return lowestLocation.toString();
    }

    public String problem2() {
        return "0";
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

        BigInteger map(BigInteger in) {
            for (MapLine mapLine : mapLines) {
                if ((in.compareTo(mapLine.source) >= 0) && (in.compareTo(mapLine.source.add(mapLine.length)) <= 0)) {
                    return mapLine.destination.add(in).subtract(mapLine.source);
                }
            }
            return in;
        }
    }

    @Data
    static class MapLine {
        final BigInteger destination;
        final BigInteger source;
        final BigInteger length;
    }
}
