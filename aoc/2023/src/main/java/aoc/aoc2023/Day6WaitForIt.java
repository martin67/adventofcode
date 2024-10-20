package aoc.aoc2023;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day6WaitForIt {
    final List<Race> races = new ArrayList<>();

    Day6WaitForIt(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s = line.split("\\s+");

            if (races.isEmpty()) {
                for (int i = 0; i < s.length - 1; i++) {
                    races.add(new Race());
                }
            }
            for (int i = 0; i < s.length - 1; i++) {
                if (s[0].equals("Time:")) {
                    races.get(i).time = Integer.parseInt(s[i + 1]);
                } else {
                    races.get(i).distance = Integer.parseInt(s[i + 1]);
                }
            }
        }
    }

    int problem1() {
        int totalWins = 1;
        for (var race : races) {
            int wins = 0;
            for (int i = 0; i < race.time; i++) {
                int distance = (race.time - i) * i;
                if (distance > race.distance) {
                    wins++;
                }
            }
            totalWins *= wins;
        }
        return totalWins;
    }

    long problem2() {
        var timeString = new StringBuilder();
        var distanceString = new StringBuilder();

        for (var race : races) {
            timeString.append(race.time);
            distanceString.append(race.distance);
        }
        long time = Long.parseLong(timeString.toString());
        long distance = Long.parseLong(distanceString.toString());

        int wins = 0;
        for (int i = 0; i < time; i++) {
            long dist = (time - i) * i;
            if (dist > distance) {
                wins++;
            }
        }
        return wins;
    }

    static class Race {
        int time;
        int distance;
    }
}
