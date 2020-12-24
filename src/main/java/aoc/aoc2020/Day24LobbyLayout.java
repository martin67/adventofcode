package aoc.aoc2020;

import aoc.Direction;
import aoc.HexPositionPointy;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day24LobbyLayout {
    List<List<Direction>> directionList = new ArrayList<>();
    Map<HexPositionPointy, Color> tiles = new HashMap<>();

    public Day24LobbyLayout(List<String> inputLines) {
        for (String line : inputLines) {
            int index = 0;
            List<Direction> directions = new ArrayList<>();
            directionList.add(directions);
            while (index < line.length()) {
                switch (line.charAt(index)) {
                    case 'e':
                        directions.add(Direction.East);
                        break;
                    case 'w':
                        directions.add(Direction.West);
                        break;
                    case 's':
                        if (line.charAt(index + 1) == 'e') {
                            directions.add(Direction.SouthEast);
                        } else {
                            directions.add(Direction.SouthWest);
                        }
                        index++;
                        break;
                    case 'n':
                        if (line.charAt(index + 1) == 'e') {
                            directions.add(Direction.NorthEast);
                        } else {
                            directions.add(Direction.NorthWest);
                        }
                        index++;
                        break;
                    default:
                        assert false : "Should not get here";
                }
                index++;
            }
        }
    }

    long problem1() {
        for (List<Direction> directions : directionList) {
            HexPositionPointy targetTile = new HexPositionPointy(0, 0);
            for (Direction direction : directions) {
                targetTile = targetTile.adjacent(direction);
            }
            tiles.putIfAbsent(targetTile, Color.White);

            if (tiles.get(targetTile) == Color.White) {
                tiles.put(targetTile, Color.Black);
            } else {
                tiles.put(targetTile, Color.White);
            }
        }
        return tiles.values().stream().filter(t -> t == Color.Black).count();
    }

    long problem2() {
        problem1();

        for (int day = 0; day < 100; day++) {
            Map<HexPositionPointy, Color> nextTiles = new HashMap<>();
            // flip
            for (HexPositionPointy position : tiles.keySet()) {
                for (HexPositionPointy positionToCheck : position.allAdjacent()) {
                    int adjacentBlack = 0;
                    for (HexPositionPointy p : positionToCheck.allAdjacent()) {
                        if (tiles.containsKey(p) && tiles.get(p) == Color.Black) {
                            adjacentBlack++;
                        }
                    }

                    if (tiles.containsKey(positionToCheck)) {
                        switch (tiles.get(positionToCheck)) {
                            case Black:
                                if (adjacentBlack == 0 || adjacentBlack > 2) {
                                    nextTiles.put(positionToCheck, Color.White);
                                } else {
                                    nextTiles.put(positionToCheck, Color.Black);
                                }
                                break;
                            case White:
                                if (adjacentBlack == 2) {
                                    nextTiles.put(positionToCheck, Color.Black);
                                } else {
                                    nextTiles.put(positionToCheck, Color.White);
                                }
                                break;
                        }
                    } else {
                        if (adjacentBlack == 2) {
                            nextTiles.put(positionToCheck, Color.Black);
                        } else {
                            nextTiles.put(positionToCheck, Color.White);
                        }
                    }
                }
            }
            tiles = nextTiles;
            //log.info("Day {}: {}", day + 1, tiles.values().stream().filter(t -> t == Color.Black).count());
        }
        return tiles.values().stream().filter(t -> t == Color.Black).count();
    }

    enum Color {Black, White}
}
