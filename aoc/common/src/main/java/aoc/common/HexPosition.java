package aoc.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

// See https://www.redblobgames.com/grids/hexagons/
// Flat topped implementation, axial coordinates

@Data
@AllArgsConstructor
public class HexPosition {
    private int col;
    private int row;

    public HexPosition adjacent(Direction direction) {
        HexPosition adj = new HexPosition(col, row);
        switch (direction) {
            case North -> adj.row--;
            case South -> adj.row++;
            case NorthEast -> {
                adj.col++;
                adj.row--;
            }
            case NorthWest -> adj.col--;
            case SouthEast -> adj.col++;
            case SouthWest -> {
                adj.col--;
                adj.row++;
            }
        }
        return adj;
    }

    public Set<HexPosition> allAdjacent() {
        Set<HexPosition> adjacent = new HashSet<>();
        adjacent.add(adjacent(Direction.North));
        adjacent.add(adjacent(Direction.South));
        adjacent.add(adjacent(Direction.NorthEast));
        adjacent.add(adjacent(Direction.NorthWest));
        adjacent.add(adjacent(Direction.SouthEast));
        adjacent.add(adjacent(Direction.SouthWest));
        return adjacent;
    }

    public int distance(HexPosition p) {
        return (Math.abs(col - p.col) + Math.abs(col + row - p.col - p.row) + Math.abs(row - p.row)) / 2;
    }
}
