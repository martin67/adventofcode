package aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.Fraction;

import java.util.*;

@Slf4j
public class Day10MonitoringStation {

    @Data
    static class Asteroid {
        Position position;
        int asteroidsDetected;

        public Asteroid(Position position) {
            this.position = position;
            asteroidsDetected = 0;
        }

        String getDirection(Asteroid target) {
            // Use Fraction but add +/- sign to numerator and denominator
            String output;

            if (this.getPosition().getX() == target.getPosition().getX()) {
                output = this.getPosition().getY() > target.getPosition().getY() ? "up" : "down";
            } else if (this.getPosition().getY() == target.getPosition().getY()) {
                output = this.getPosition().getX() > target.getPosition().getX() ? "left" : "right";
            } else {
                Fraction f = Fraction.getReducedFraction(target.getPosition().getX() - this.getPosition().getX(),
                        target.getPosition().getY() - this.getPosition().getY());
                int numerator = f.getNumerator();
                int denominator = f.getDenominator();
                if (this.getPosition().getY() > target.getPosition().getY()) {
                    denominator = -denominator;
                }
                output = String.format("%d,%d", numerator, denominator);
            }
            return output;
        }

        float getAngle(Asteroid target) {
            return
        }
        @Override
        public String toString() {
            return "Asteroid{" +
                    "position=" + position +
                    '}';
        }
    }

    private List<Asteroid> asteroids;

    public Day10MonitoringStation(List<String> map) {
        int y = 0;
        this.asteroids = new ArrayList<>();

        for (String row : map) {
            int x = 0;
            for (char pos : row.toCharArray()) {
                if (pos == '#') {
                    asteroids.add(new Asteroid(new Position(x, y)));
                }
                x++;
            }
            y++;
        }
    }

    int bestLocation() {
        for (Asteroid asteroid : asteroids) {
            Map<String, Integer> directions = new HashMap<>();

            for (Asteroid target : asteroids) {
                if (asteroid != target) {
                    String direction = asteroid.getDirection(target);
                    if (directions.containsKey(direction)) {
                        directions.put(direction, directions.get(direction) + 1);
                    } else {
                        directions.put(direction, 1);
                    }
                }
            }
            asteroid.setAsteroidsDetected(directions.size());
        }

        Asteroid mostDetected = asteroids.stream()
                .max(Comparator.comparing(Asteroid::getAsteroidsDetected))
                .orElseThrow(NoSuchElementException::new);
        log.info("Most detected from {}", mostDetected);
        return mostDetected.asteroidsDetected;
    }

    int vaporize() {
        Asteroid laser = new Asteroid(new Position(8,3));

        asteroids.stream().sorted(a -> {})
        return 0;
    }
}
