package aoc.aoc2019;

import aoc.Position;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.Fraction;

import java.util.*;
import java.util.stream.Collectors;

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

        double getAngle(Asteroid target) {
            double dx = target.position.getX() - this.position.getX();
            double dy = target.position.getY() - this.position.getY();

            return Math.atan2(dx, dy);
        }

        int getDistance(Asteroid target) {
            return this.position.distance(target.position);
        }

        @Override
        public String toString() {
            return "Asteroid{" + position + '}';
        }
    }

    private final List<Asteroid> asteroids;
    private Asteroid mostDetected;

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

        mostDetected = asteroids.stream()
                .max(Comparator.comparing(Asteroid::getAsteroidsDetected))
                .orElseThrow(NoSuchElementException::new);
        log.info("Most detected from {}", mostDetected);
        return mostDetected.asteroidsDetected;
    }

    int vaporize() {
        bestLocation();
        Asteroid laser = mostDetected;

        List<Asteroid> targetList = asteroids.stream()
                .sorted(Comparator.comparingDouble(laser::getAngle).reversed()
                        .thenComparing(laser::getDistance)).collect(Collectors.toList());

        List<Asteroid> zappedTargets = new ArrayList<>();
        do {
            double previousTargetAngle = -999.0;
            List<Asteroid> nextTargetList = new ArrayList<>();
            for (Asteroid target : targetList) {
                if (previousTargetAngle == -999.0) {
                    previousTargetAngle = laser.getAngle(target);
                    log.debug("Zapping initial target " + target);
                    zappedTargets.add(target);
                } else {
                    if (laser.getAngle(target) == previousTargetAngle) {
                        log.debug("Skipping target " + target);
                        nextTargetList.add(target);
                    } else {
                        previousTargetAngle = laser.getAngle(target);
                        log.debug("Zapping target " + target);
                        zappedTargets.add(target);
                    }
                }
            }
            targetList = nextTargetList;
        } while (!targetList.isEmpty());

        return zappedTargets.get(200 - 1).position.getX() * 100 + zappedTargets.get(200 - 1).position.getY();
    }
}