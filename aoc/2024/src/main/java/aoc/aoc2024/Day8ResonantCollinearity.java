package aoc.aoc2024;

import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day8ResonantCollinearity {
    Map<Position, Character> map = new HashMap<>();
    Set<Character> antennas = new HashSet<>();
    int width;
    int height;

    Day8ResonantCollinearity(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c != '.') {
                    map.put(new Position(x, y), c);
                    antennas.add(c);
                }
                x++;
            }
            width = x;
            y++;
        }
        height = y;
    }

    int problem1() {
        Set<Position> antinodes = new HashSet<>();
        for (char antenna : antennas) {
            Set<Position> positions = map.entrySet().stream()
                    .filter(e -> e.getValue().equals(antenna))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
            antinodes.addAll(allAntinodes(positions));
        }
        return antinodes.size();
    }

    int problem2() {
        Set<Position> antinodes = new HashSet<>();
        for (char antenna : antennas) {
            Set<Position> positions = map.entrySet().stream()
                    .filter(e -> e.getValue().equals(antenna))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());

            for (Position start : positions) {
                for (Position end : positions) {
                    if (!start.equals(end)) {
                        Set<Position> a = new HashSet<>();
                        Set<Position> b = new HashSet<>(Set.of(start, end));
                        while (a.size() != b.size()) {
                            a = new HashSet<>(b);
                            b.addAll(allAntinodes(a));
                        }
                        antinodes.addAll(a);
                    }
                }
            }
        }
        return antinodes.size();
    }

    Set<Position> allAntinodes(Set<Position> positions) {
        Set<Position> antinodes = new HashSet<>();
        for (Position start : positions) {
            for (Position end : positions) {
                if (!start.equals(end)) {
                    antinodes.addAll(antinodes(start, end));
                }
            }
        }
        return antinodes;
    }

    Set<Position> antinodes(Position a, Position b) {
        Set<Position> antinodes = new HashSet<>();
        int dX = Math.abs(a.getX() - b.getX());
        int dY = Math.abs(a.getY() - b.getY());
        Position pos1 = new Position();
        Position pos2 = new Position();
        if (a.getX() > b.getX()) {
            pos1.setX(a.getX() + dX);
            pos2.setX(b.getX() - dX);
        } else {
            pos1.setX(a.getX() - dX);
            pos2.setX(b.getX() + dX);
        }
        if (a.getY() > b.getY()) {
            pos1.setY(a.getY() + dY);
            pos2.setY(b.getY() - dY);
        } else {
            pos1.setY(a.getY() - dY);
            pos2.setY(b.getY() + dY);
        }
        if (pos1.getX() >= 0 && pos1.getX() < width && pos1.getY() >= 0 && pos1.getY() < height) {
            antinodes.add(pos1);
        }
        if (pos2.getX() >= 0 && pos2.getX() < width && pos2.getY() >= 0 && pos2.getY() < height) {
            antinodes.add(pos2);
        }
        return antinodes;
    }
}
