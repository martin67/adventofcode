package aoc.aoc2022;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day23UnstableDiffusion {

    Set<Elf> elves = new HashSet<>();

    public Day23UnstableDiffusion(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == '#') {
                    elves.add(new Elf(new Position(x, y)));
                }
                x++;
            }
            y++;
        }
    }

    int problem1() {
        Set<Position> elfPos;
//        Position.printMap(elves.stream().map(Elf::getPosition).collect(Collectors.toSet()), "=== Initial State ===");
        for (int i = 0; i < 10; i++) {
            elfPos = elves.stream().map(Elf::getPosition).collect(Collectors.toSet());
            Map<Position, Integer> proposalCount = new HashMap<>();
            for (Elf elf : elves) {
                if (elf.hasNeighbour(elfPos)) {
                    Position proposal = elf.propose(elfPos, i);
                    proposalCount.merge(proposal, 1, Integer::sum);
                }
            }
            Set<Position> duplicateProposals = proposalCount.entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
            // change all elves that propose to move to the same position back to their starting position
            elves.stream()
                    .filter(e -> duplicateProposals.contains(e.proposedPosition))
                    .forEach(e -> e.proposedPosition = e.position);

            // move all elves:
            for (Elf elf : elves) {
                if (elf.proposedPosition != null) {
                    elf.position = elf.proposedPosition;
                    elf.proposedPosition = null;
                }
            }
//            Position.printMap(elves.stream().map(Elf::getPosition).collect(Collectors.toSet()), String.format("=== End of round %d ===", i + 1));
        }

        elfPos = elves.stream().map(Elf::getPosition).collect(Collectors.toSet());
        int xMin = elfPos.stream().mapToInt(Position::getX).min().orElseThrow(NoSuchElementException::new);
        int xMax = elfPos.stream().mapToInt(Position::getX).max().orElseThrow(NoSuchElementException::new);
        int yMin = elfPos.stream().mapToInt(Position::getY).min().orElseThrow(NoSuchElementException::new);
        int yMax = elfPos.stream().mapToInt(Position::getY).max().orElseThrow(NoSuchElementException::new);

        return ((xMax - xMin + 1) * (yMax - yMin + 1)) - elfPos.size();
    }

    int problem2() {
        Map<Position, Integer> proposalCount;
        int rounds = 0;
        do {
            Set<Position> elfPos = elves.stream().map(Elf::getPosition).collect(Collectors.toSet());
            proposalCount = new HashMap<>();
            for (Elf elf : elves) {
                if (elf.hasNeighbour(elfPos)) {
                    Position proposal = elf.propose(elfPos, rounds);
                    proposalCount.merge(proposal, 1, Integer::sum);
                }
            }
            Set<Position> duplicateProposals = proposalCount.entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
            // change all elves that propose to move to the same position back to their starting position
            elves.stream()
                    .filter(e -> duplicateProposals.contains(e.proposedPosition))
                    .forEach(e -> e.proposedPosition = e.position);

            // move all elves:
            for (Elf elf : elves) {
                if (elf.proposedPosition != null) {
                    elf.position = elf.proposedPosition;
                    elf.proposedPosition = null;
                }
            }
            rounds++;
        } while (!proposalCount.isEmpty());

        return rounds;
    }

    static class Elf {
        Position position;
        Position proposedPosition;

        public Elf(Position position) {
            this.position = position;
        }

        boolean hasNeighbour(Set<Position> elfPositions) {
            for (Position p : position.allAdjacentIncludingDiagonal()) {
                if (elfPositions.contains(p)) {
                    return true;
                }
            }
            return false;
        }

        Position propose(Set<Position> elfPositions, int round) {
            proposedPosition = null;
            switch (round % 4) {
                case 0 -> {
                    proposeNorth(elfPositions);
                    proposeSouth(elfPositions);
                    proposeWest(elfPositions);
                    proposeEast(elfPositions);
                }
                case 1 -> {
                    proposeSouth(elfPositions);
                    proposeWest(elfPositions);
                    proposeEast(elfPositions);
                    proposeNorth(elfPositions);
                }
                case 2 -> {
                    proposeWest(elfPositions);
                    proposeEast(elfPositions);
                    proposeNorth(elfPositions);
                    proposeSouth(elfPositions);
                }
                case 3 -> {
                    proposeEast(elfPositions);
                    proposeNorth(elfPositions);
                    proposeSouth(elfPositions);
                    proposeWest(elfPositions);
                }
            }
            return proposedPosition;
        }

        void proposeNorth(Set<Position> elfPositions) {
            if (proposedPosition == null) {
                if (!elfPositions.contains(position.adjacent(Direction.North)) &&
                        !elfPositions.contains((position.adjacent(Direction.NorthEast))) &&
                        !elfPositions.contains(position.adjacent(Direction.NorthWest))) {
                    proposedPosition = position.adjacent(Direction.North);
                }
            }
        }

        void proposeSouth(Set<Position> elfPositions) {
            if (proposedPosition == null) {
                if (!elfPositions.contains(position.adjacent(Direction.South)) &&
                        !elfPositions.contains((position.adjacent(Direction.SouthEast))) &&
                        !elfPositions.contains(position.adjacent(Direction.SouthWest))) {
                    proposedPosition = position.adjacent(Direction.South);
                }
            }
        }

        void proposeWest(Set<Position> elfPositions) {
            if (proposedPosition == null) {
                if (!elfPositions.contains(position.adjacent(Direction.West)) &&
                        !elfPositions.contains((position.adjacent(Direction.NorthWest))) &&
                        !elfPositions.contains(position.adjacent(Direction.SouthWest))) {
                    proposedPosition = position.adjacent(Direction.West);
                }
            }
        }

        void proposeEast(Set<Position> elfPositions) {
            if (proposedPosition == null) {
                if (!elfPositions.contains(position.adjacent(Direction.East)) &&
                        !elfPositions.contains((position.adjacent(Direction.NorthEast))) &&
                        !elfPositions.contains(position.adjacent(Direction.SouthEast))) {
                    proposedPosition = position.adjacent(Direction.East);
                }
            }
        }

        public Position getPosition() {
            return position;
        }
    }
}
