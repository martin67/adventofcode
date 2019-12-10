package aoc2019;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.Fraction;
import org.w3c.dom.ranges.DocumentRange;

import java.util.*;

@Slf4j
public class Day10MonitoringStation {

    @Data
    static class Asteroid {
        Position position;
        int asteroidsDetected;
        int asteroidsDetected2;
        Map<Fraction, Integer> angles = new HashMap<>();
        Map<String, Integer> directions = new HashMap<>();

        public Asteroid(Position position) {
            this.position = position;
            asteroidsDetected = 0;
            asteroidsDetected2 = 0;
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
                if (this.getPosition().getX() < target.getPosition().getX()) {
                    numerator = -numerator;
                }
                if (this.getPosition().getY() < target.getPosition().getY()) {
                    denominator = -denominator;
                }
                output = String.format("%d/%d", numerator, denominator);
            }
            return output;
        }

        Fraction getAngle(Asteroid target) {
            // Skip angles that are on the straight lines
            if (this.getPosition().getX() == target.getPosition().getX() ||
                    this.getPosition().getY() == target.getPosition().getY()) {
                return null;
            } else {
                return Fraction.getReducedFraction(target.getPosition().getX() - this.getPosition().getX(),
                        target.getPosition().getY() - this.getPosition().getY());
            }
        }
    }

    private List<Asteroid> asteroids;

    public Day10MonitoringStation(List<String> map) {
        int x = 0;
        int y = 0;
        this.asteroids = new ArrayList<>();

        for (String row : map) {
            x = 0;
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
            //Map<Fraction, Integer> angles = new HashMap<>();
            boolean up = false;
            boolean down = false;
            boolean left = false;
            boolean right = false;

            for (Asteroid target : asteroids) {
                if (asteroid != target) {
                    Fraction angle = asteroid.getAngle(target);
                    String direction = asteroid.getDirection(target);

                    if (asteroid.directions.containsKey(direction)) {
                        asteroid.directions.put(direction, asteroid.directions.get(direction) + 1);
                    } else {
                        asteroid.directions.put(direction, 1);
                    }

                    if (angle != null) {
                        if (asteroid.angles.containsKey(angle)) {
                            asteroid.angles.put(angle, asteroid.angles.get(angle) + 1);
                        } else {
                            asteroid.angles.put(angle, 1);
                        }
                    } else {
                        // on the lines?
                        if (target.getPosition().getX() > asteroid.getPosition().getX()) {
                            right = true;
                        }
                        if (target.getPosition().getX() < asteroid.getPosition().getX()) {
                            left = true;
                        }
                        if (target.getPosition().getY() > asteroid.getPosition().getY()) {
                            down = true;
                        }
                        if (target.getPosition().getY() < asteroid.getPosition().getY()) {
                            up = true;
                        }
                    }
                }
            }
            asteroid.setAsteroidsDetected(asteroid.angles.size());
            asteroid.setAsteroidsDetected2(asteroid.directions.size());
            if (left) asteroid.asteroidsDetected++;
            if (right) asteroid.asteroidsDetected++;
            if (up) asteroid.asteroidsDetected++;
            if (down) asteroid.asteroidsDetected++;
        }

        Asteroid mostDetected = asteroids.stream()
                .max(Comparator.comparing(Asteroid::getAsteroidsDetected))
                .orElseThrow(NoSuchElementException::new);
        Asteroid mostDetected2 = asteroids.stream()
                .max(Comparator.comparing(Asteroid::getAsteroidsDetected2))
                .orElseThrow(NoSuchElementException::new);
        log.info("Most detected from {}", mostDetected);
        log.info("Most detected2 from {}", mostDetected2);
        return mostDetected2.asteroidsDetected;
    }
}
