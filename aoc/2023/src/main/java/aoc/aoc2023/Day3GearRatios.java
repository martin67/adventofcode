package aoc.aoc2023;

import aoc.common.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3GearRatios {

    Set<Number> numbers = new HashSet<>();
    Set<Position> symbols = new HashSet<>();
    Set<Position> gears = new HashSet<>();

    public Day3GearRatios(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            boolean numberActive = false;
            Number number = null;
            for (char c : line.toCharArray()) {
                Position position = new Position(x, y);
                if (Character.isDigit(c)) {
                    if (!numberActive) {
                        number = new Number();
                        numbers.add(number);
                        numberActive = true;
                    }
                    number.add(position, c);
                } else {
                    numberActive = false;
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

        for (Position pos : symbols) {
            for (Number number : numbers) {
                if (pos.allAdjacentIncludingDiagonal().stream().anyMatch(number.positions::contains)) {
                    foundNumbers.add(number);
                }
            }
        }

        return foundNumbers.stream().mapToInt(Number::getNumber).sum();
    }

    public int problem2() {
        int gearRatios = 0;

        for (Position pos : gears) {
            Set<Number> foundNumbers = new HashSet<>();

            for (Number number : numbers) {
                if (pos.allAdjacentIncludingDiagonal().stream().anyMatch(number.positions::contains)) {
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
        List<Position> positions = new ArrayList<>();
        String value = "";

        void add(Position position, char c) {
            positions.add(position);
            value += c;
        }

        int getNumber() {
            return Integer.parseInt(value);
        }
    }
}
