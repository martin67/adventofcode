package aoc.aoc2022;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class Day9RopeBridge {

    List<Motion> motions = new ArrayList<>();
    Set<Position> tailVisit = new HashSet<>();

    public Day9RopeBridge(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s = line.split(" ");
            Direction direction = switch (s[0]) {
                case "U" -> Direction.Up;
                case "D" -> Direction.Down;
                case "L" -> Direction.Left;
                case "R" -> Direction.Right;
                default -> throw new IllegalArgumentException("Invalid direction: " + s[0]);
            };
            int steps = Integer.parseInt(s[1]);
            motions.add(new Motion(direction, steps));
        }
    }

    int problem1() {
        Position head = new Position(0, 0);
        Position tail = head;
        tailVisit.add(tail);

        for (Motion motion : motions) {
            for (int i = 0; i < motion.steps; i++) {
                head = head.adjacent(motion.direction);
                tail = moveTail(head, tail);
                tailVisit.add(tail);
            }
        }
        return tailVisit.size();
    }

    int problem2() {
        List<Position> rope = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            rope.add(new Position(0, 0));
        }
        tailVisit.add(new Position(0, 0));
        // head = rope(0), tail = rope(9)

        for (Motion motion : motions) {
            for (int i = 0; i < motion.steps; i++) {
                // move head
                rope.set(0, rope.get(0).adjacent(motion.direction));
                // move rest of rope
                for (int j = 1; j < 10; j++) {
                    rope.set(j, moveTail(rope.get(j - 1), rope.get(j)));
                }
                tailVisit.add(rope.get(9));
            }
        }
        return tailVisit.size();
    }

    Position moveTail(Position head, Position tail) {
        if (tail.equals(head) || tail.allAdjacentIncludingDiagonal().contains(head)) {
            return tail;
        } else {
            return switch (tail.directionTo(head, false)) {
                case North -> head.adjacent(Direction.Down);
                case South -> head.adjacent(Direction.Up);
                case East -> head.adjacent(Direction.West);
                case West -> head.adjacent(Direction.East);
                case Unknown -> {
                    // move diagonally
                    Set<Position> adjacentToHead = head.allAdjacent();
                    Set<Position> allPossibleTailMoves = tail.allAdjacentIncludingDiagonal();
                    allPossibleTailMoves.retainAll(adjacentToHead);
                    if (allPossibleTailMoves.isEmpty()) {
                        // special case for corner
                        adjacentToHead = head.allAdjacentIncludingDiagonal();
                        allPossibleTailMoves = tail.allAdjacentIncludingDiagonal();
                        allPossibleTailMoves.retainAll(adjacentToHead);
                    }
                    yield allPossibleTailMoves.iterator().next();
                }
                default -> throw new IllegalStateException("Unexpected value: " + tail.directionTo(head, false));
            };
        }
    }

    static class Motion {
        Direction direction;
        int steps;

        public Motion(Direction direction, int steps) {
            this.direction = direction;
            this.steps = steps;
        }
    }
}
