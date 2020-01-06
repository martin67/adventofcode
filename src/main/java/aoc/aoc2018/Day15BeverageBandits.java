package aoc.aoc2018;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Data
class BfsNode {
    Position position;
    BfsNode parent;

    BfsNode(Position position) {
        this.position = position;
    }
}

@Data
@AllArgsConstructor
class Unit {
    Position position;
    Day15BeverageBandits.Type type;
    int attackPower;
    int hitPoints;
}


@Slf4j
public class Day15BeverageBandits {

    enum Type {Elf, Goblin}


    @Data
    @AllArgsConstructor
    class Space {
        Position position;
        Type type;
        int hitPoints;
    }

    private Set<Position> walls;
    private List<Unit> units;
    private int mapWidth;
    private int mapHeight;
    private boolean gameOver;
    private int finalHitPoints;
    private int numberOfElves;
    private int numberOfGoblins;


    private void init(String input) {
        walls = new HashSet<>();
        units = new ArrayList<>();
        gameOver = false;
        finalHitPoints = 0;
        numberOfElves = 0;
        numberOfGoblins = 0;
        readMap(input);
    }

    private void setElvesAttackPower(int attackPower) {
        units.stream().filter(u -> u.getType().equals(Type.Elf)).forEach(u -> u.setAttackPower(attackPower));
    }

    private void readMap(String input) {
        List<String> inputStrings = Arrays.stream(input.split("\\n+"))
                .collect(Collectors.toList());

        int x = 0;
        int y = 0;
        for (String row : inputStrings) {
            x = 0;
            for (char c : row.toCharArray()) {
                Position position = new Position(x, y);
                switch (c) {
                    case '#':
                        walls.add(position);
                        break;
                    case 'E':
                        units.add(new Unit(position, Type.Elf, 3, 200));
                        numberOfElves++;
                        break;
                    case 'G':
                        units.add(new Unit(position, Type.Goblin, 3, 200));
                        numberOfGoblins++;
                        break;
                }
                x++;
            }
            y++;
        }
        mapWidth = x;
        mapHeight = y;
    }

    private void printMap(Set<Position> extraSet, char symbol) {
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                Position position = new Position(x, y);
                if (extraSet != null && extraSet.contains(position)) {
                    System.out.print(symbol);
                } else if (walls.contains(position)) {
                    System.out.print('#');
                } else {
                    Unit unit = units.stream().filter(u -> u.getPosition().equals(position)).findFirst().orElse(null);
                    if (unit == null) {
                        System.out.print('.');
                    } else {
                        if (unit.getType() == Type.Elf) {
                            System.out.print('E');
                        } else {
                            System.out.print('G');
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    int computeCombatOutcome(String input) {
        init(input);
        return computeCombat();
    }

    private int computeCombat() {
        int rounds = 1;
        while (!gameOver) {
            System.out.println("------------------- Starting game round " + rounds);

            for (Unit unit : units.stream().sorted(Comparator.comparing(Unit::getPosition)).collect(Collectors.toList())) {

                // Exit if there are no more targets remaining
                if ((unit.getType() == Type.Elf && numberOfGoblins == 0) ||
                        (unit.getType() == Type.Goblin && numberOfElves == 0)) {
                    //System.out.println("exit before!!!!!!!!!");
                    gameOver = true;
                    finalHitPoints = units.stream().mapToInt(Unit::getHitPoints).sum();
                    //printGameOver(rounds);
                } else if (unit.getHitPoints() > 0) {

                    //printMap(null, ' ');
                    //System.out.println("Processing unit: " + unit);

                    // attack if possible
                    if (!attack(unit)) {
                        // nu.hagelin.adventofcode.cal2018.Unit tries to move instead

                        // identify targets => alla unit of different type than self
                        Type targetType = unit.getType() == Type.Elf ? Type.Goblin : Type.Elf;
                        List<Unit> targets = units.stream().filter(u -> u.getType() == targetType).collect(Collectors.toList());

                        // list all possible positions for all targets
                        Set<Position> positionsInRange = new HashSet<>();
                        for (Unit target : targets) {
                            positionsInRange.addAll(target.getPosition().adjacent());
                        }
                        // remove all positions that have a wall or a unit in them
                        positionsInRange.removeAll(new ArrayList<>(walls));
                        positionsInRange.removeAll(units.stream().map(Unit::getPosition).collect(Collectors.toList()));
                        //printMap(positionsInRange, '?');

                        // nearest
                        // compute distance to all possible positions
                        List<Position> superShort = null;
                        int lengthOfShortestList = Integer.MAX_VALUE;

                        for (Position pos : positionsInRange) {
                            List<Position> shortestPath = shortestPath(unit.getPosition(), pos);
                            if (shortestPath != null) {
                                //printMap(new HashSet<>(shortestPath), '>');
                                if (shortestPath.size() < lengthOfShortestList) {
                                    superShort = shortestPath;
                                    lengthOfShortestList = shortestPath.size();
                                } else if (shortestPath.size() == lengthOfShortestList) {
                                    if (superShort.get(superShort.size() - 1).compareTo(pos) > 0) {
                                        superShort = shortestPath;
                                    }
                                }
                                //System.out.println("Shortest path from " + unit.getPosition() + " to " + pos + " distance: " + shortestPath.size());
                            }
                        }

                        // Move unit
                        if (superShort != null) {
                            //System.out.println("Moving unit " + unit + " to " + superShort.get(0));
                            unit.setPosition(superShort.get(0));

                            // Attack
                            attack(unit);
                        }
                    }
                }
            }

            System.out.println("------------------- Finished game round " + rounds);
            rounds++;

        }
        rounds -= 2;
        printGameOver(rounds);
        return rounds * finalHitPoints;
    }

    private LinkedList<Position> shortestPath(Position start, Position end) {
        //log.info("Compute distance from " + start + " to " + end);
        Queue<BfsNode> positionsTodo = new LinkedList<>();
        Set<BfsNode> positionsDone = new HashSet<>();

        positionsTodo.add(new BfsNode(start));
        while (!positionsTodo.isEmpty()) {
            BfsNode activePosition = positionsTodo.remove();
            if (activePosition.position.equals(end)) {
                // traverse from end to start
                LinkedList<Position> path = new LinkedList<>();
                BfsNode node = activePosition;
                while (node.parent != null) {
                    path.addFirst(node.position);
                    node = node.parent;
                }
                return path;
            } else {
                positionsDone.add(activePosition);

                // add adjacent open nodes to posisitionsTodo
                activePosition.position.adjacent().stream()
                        .filter(p -> !walls.contains(p))
                        .filter(p -> !units.stream().map(Unit::getPosition).collect(Collectors.toSet()).contains(p))
                        .filter(p -> !positionsDone.stream().map(BfsNode::getPosition).collect(Collectors.toSet()).contains(p))
                        .filter(p -> !positionsTodo.stream().map(BfsNode::getPosition).collect(Collectors.toSet()).contains(p))
                        .sorted(Comparator.naturalOrder())
                        .forEach(p -> {
                            BfsNode node = new BfsNode(p);
                            node.parent = activePosition;
                            positionsTodo.add(node);
                        });
            }
        }
        // no path found
        return null;
    }

    private Unit getUnit(Position position) {
        return units.stream().filter(unit -> unit.getPosition().equals(position))
                .findFirst().orElse(null);
    }

    private List<Unit> getAdjacentUnits(Position position) {
        List<Unit> unitList = new ArrayList<>();

        for (Position p : position.adjacent()) {
            Unit unit = getUnit(p);
            if (unit != null) {
                unitList.add(unit);
            }
        }
        return unitList;
    }

    private boolean attack(Unit attacker) {
        // Get defenders
        Type targetType = attacker.getType() == Type.Elf ? Type.Goblin : Type.Elf;
        boolean attacked = false;

        Unit defender = getAdjacentUnits(attacker.position).stream()
                .filter(unit -> unit.getType().equals(targetType))
                .min(Comparator.comparing(Unit::getHitPoints).thenComparing(Unit::getPosition))
                .orElse(null);

        /*List<nu.hagelin.adventofcode.cal2018.Unit> adjacentTargets = getAdjacentUnits(attacker.position).stream()
                .filter(unit -> unit.getType().equals(targetType)).collect(Collectors.toList());
        if (adjacentTargets.size() > 1) {
            System.out.println("**  Number of adjacent targets: " + adjacentTargets.size());
            System.out.println("**  Attacker: " + attacker);
            for (nu.hagelin.adventofcode.cal2018.Unit u : adjacentTargets) {
                System.out.println("**  Defender: " + u);
            }
            System.out.println("**  Selected defender: " + defender);
        }*/

        if (defender != null) {
            System.out.println("Attack! " + attacker + " attacking " + defender);
            defender.hitPoints -= attacker.getAttackPower();
            if (defender.hitPoints <= 0) {
                System.out.println("Target " + defender + " eliminated!");
                if (defender.getType() == Type.Elf) {
                    numberOfElves--;
                }
                if (defender.getType() == Type.Goblin) {
                    numberOfGoblins--;
                }
                units.remove(defender);
            }
            attacked = true;
        }
        return attacked;
    }

    private void printGameOver(int rounds) {
        System.out.println("*** Game over ***");
        int score = rounds * finalHitPoints;
        System.out.println("Score: " + score + " ( " + rounds + " x " + finalHitPoints + " )");
        System.out.println("Remaining units:");
        units.stream().sorted(Comparator.comparing(Unit::getPosition)).forEach(System.out::println);
    }

    int computeCombatOutcomeNoDeadElves(String input) {
        int elvesAttackPower = 3;
        int numberOfDeadElves = 0;
        int result;
        do {
            int startingNumberOfElves;

            init(input);
            startingNumberOfElves = numberOfElves;
            setElvesAttackPower(elvesAttackPower++);
            result = computeCombat();
            numberOfDeadElves = startingNumberOfElves - numberOfElves;
        } while (numberOfDeadElves > 0);

        return result;
    }
}