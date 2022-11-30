package aoc.aoc2021;

import aoc.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day13TransparentOrigami {

    final Set<Position> dots = new HashSet<>();
    final List<Instruction> instructions = new ArrayList<>();
    int xSize;
    int ySize;

    public Day13TransparentOrigami(List<String> inputLines) {
        Pattern dotPattern = Pattern.compile("(\\d+),(\\d+)");
        Pattern foldPattern = Pattern.compile("fold along (\\w)=(\\d+)");
        Matcher matcher;
        for (String line : inputLines) {
            matcher = dotPattern.matcher(line);
            if (matcher.find()) {
                dots.add(new Position(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
            }
            matcher = foldPattern.matcher(line);
            if (matcher.find()) {
                instructions.add(new Instruction(matcher.group(1), Integer.parseInt(matcher.group(2))));
            }
        }
        xSize = dots.stream().mapToInt(Position::getX).max().orElseThrow(NoSuchElementException::new) + 1;
        ySize = dots.stream().mapToInt(Position::getY).max().orElseThrow(NoSuchElementException::new) + 1;
        // This was a bit illogical. y needs to be odd...
        if (ySize % 2 == 0) {
            ySize++;
        }
    }

    int problem1() {
        Set<Position> newSet = fold(instructions.get(0), dots);
        return newSet.size();
    }

    int problem2() {
        Set<Position> newSet = new HashSet<>(dots);
        for (Instruction instruction : instructions) {
            newSet = fold(instruction, newSet);
        }
        printPaper(newSet);
        return 0;
    }

    Set<Position> fold(Instruction instruction, Set<Position> dotSet) {
        Set<Position> newDots = new HashSet<>();
        log.info("Folding {} at {}, size {}x{}", instruction.direction, instruction.line, xSize, ySize);
        switch (instruction.direction) {
            case "x" -> {
                for (Position p : dotSet) {
                    if (p.getX() < instruction.line) {
                        newDots.add(p);
                    } else if (p.getX() > instruction.line) {
                        newDots.add(new Position(xSize - p.getX() - 1, p.getY()));
                    }
                }
                xSize = xSize / 2;
            }
            case "y" -> {
                for (Position p : dotSet) {
                    if (p.getY() < instruction.line) {
                        newDots.add(p);
                    } else if (p.getY() > instruction.line) {
                        newDots.add(new Position(p.getX(), ySize - p.getY() - 1));
                    }
                }
                ySize = ySize / 2;
            }
        }
        return newDots;
    }

    void printPaper(Set<Position> dotSet) {
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                if (dotSet.contains(new Position(x, y))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    static class Instruction {
        final String direction;
        final int line;

        public Instruction(String direction, int line) {
            this.direction = direction;
            this.line = line;
        }
    }
}
