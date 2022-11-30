package aoc.aoc2015;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Data
public class Day14ReindeerOlympics {

    private Set<Reindeer> reindeers = new HashSet<>();

    public Day14ReindeerOlympics(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.$");

        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                reindeers.add(new Reindeer(matcher.group(1),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4))));
            }
        }
    }

    public int distanceTraveled(int time) {
        for (int i = 0; i < time; i++) {
            //log.info("{} {} . {}", i, reindeers.get(0).name, reindeers.get(0).flying(i));
            for (Reindeer reindeer : reindeers) {
                if (reindeer.isFlying(i)) {
                    reindeer.totalDistance += reindeer.speed;
                }
            }
        }
        return reindeers.stream().max(Comparator.comparing(Reindeer::getTotalDistance)).get().totalDistance;
    }

    public int winningPoints(int time) {
        for (int i = 0; i < time; i++) {
            //log.info("{} {} . {}", i, reindeers.get(0).name, reindeers.get(0).flying(i));
            for (Reindeer reindeer : reindeers) {
                if (reindeer.isFlying(i)) {
                    reindeer.totalDistance += reindeer.speed;
                }
            }
            int leaderDistance = reindeers.stream().max(Comparator.comparing(Reindeer::getTotalDistance)).get().totalDistance;
            reindeers.stream().filter(r -> r.totalDistance == leaderDistance).forEach(r2 -> r2.points++);
        }
        return reindeers.stream().max(Comparator.comparing(Reindeer::getPoints)).get().points;
    }

    @Data
    static
    class Reindeer {
        String name;
        int speed;
        int flyingTime;
        int restingTime;
        int totalDistance;
        int points;

        public Reindeer(String name, int speed, int flyingTime, int restingTime) {
            this.name = name;
            this.speed = speed;
            this.flyingTime = flyingTime;
            this.restingTime = restingTime;
            this.totalDistance = 0;
            this.points = 0;
        }

        boolean isFlying(int time) {
            int d = time % (flyingTime + restingTime);
            return d < flyingTime;
        }
    }
}
