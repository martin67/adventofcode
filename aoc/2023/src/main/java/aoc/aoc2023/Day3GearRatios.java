package aoc.aoc2023;

import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class Day3GearRatios {

    final Set<Number> numbers = new HashSet<>();
    final Set<Position> symbols = new HashSet<>();
    final Set<Position> gears = new HashSet<>();

    public Day3GearRatios(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            Number number = null;
            for (char c : line.toCharArray()) {
                var position = new Position(x, y);
                if (Character.isDigit(c)) {
                    if (number == null) {
                        number = new Number();
                        numbers.add(number);
                    }
                    number.add(position, c);
                } else {
                    number = null;
                    if (c != '.') {
                        symbols.add(position);
                        if (c == '*') {
                            gears.add(position);
                        }
                    }
                }
                x++;
            }
            y++;
        }
    }

    public int problem1() {
        Set<Number> foundNumbers = new HashSet<>();

        for (var position : symbols) {
            Set<Position> neighbourPositions = position.allAdjacentIncludingDiagonal();
            for (var number : numbers) {
                if (neighbourPositions.stream().anyMatch(number.positions::contains)) {
                    foundNumbers.add(number);
                }
            }
        }

        return foundNumbers.stream().mapToInt(Number::getNumber).sum();
    }

    public int problem2() {
        int gearRatios = 0;

        for (var position : gears) {
            Set<Number> foundNumbers = new HashSet<>();
            Set<Position> neighbourPositions = position.allAdjacentIncludingDiagonal();

            for (var number : numbers) {
                if (neighbourPositions.stream().anyMatch(number.positions::contains)) {
                    foundNumbers.add(number);
                }
            }
            if (foundNumbers.size() == 2) {
                gearRatios += foundNumbers.stream().mapToInt(Number::getNumber).reduce(1, (a, b) -> a * b);
            }
        }

        return gearRatios;
    }

    static class Number {
        final List<Position> positions = new ArrayList<>();
        final StringBuilder stringBuilder = new StringBuilder();

        void add(Position position, char c) {
            positions.add(position);
            stringBuilder.append(c);
        }

        int getNumber() {
            return Integer.parseInt(stringBuilder.toString());
        }
    }
}
