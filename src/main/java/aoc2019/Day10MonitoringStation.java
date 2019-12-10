package aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.Fraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Fraction getAngle(Asteroid target) {
            return Fraction.getReducedFraction(this.getPosition().getX() - target.getPosition().getX(),
                    this.getPosition().getY() - target.getPosition().getY());
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
            Map<Fraction, Integer> angles = new HashMap<>();
            for (Asteroid target : asteroids) {
                if (asteroid != target) {
                    Fraction angle = asteroid.getAngle(target);
                    if (angles.containsKey(angle)) {
                        angles.put(angle, angles.get(angle) + 1);
                    } else {
                        angles.put(angle, 1);
                    }
                }
            }
            asteroid.setAsteroidsDetected(angles.size());
        }
        return 0;
    }
}
