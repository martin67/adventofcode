package aoc.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

// See https://www.redblobgames.com/grids/hexagons/
// Pointy topped implementation, axial coordinates

@Data
@AllArgsConstructor
public class HexPositionPointy {
    private int col;
    private int row;

    public HexPositionPointy adjacent(Direction direction) {
        HexPositionPointy adj = new HexPositionPointy(col, row);
        switch (direction) {
            case NorthEast -> {
                adj.col++;
                adj.row--;
            }
            case NorthWest -> adj.row--;
            case SouthEast -> adj.row++;
            case SouthWest -> {
                adj.col--;
                adj.row++;
            }
            case East -> adj.col++;
            case West -> adj.col--;
        }
        return adj;
    }

    public Set<HexPositionPointy> allAdjacent() {
        Set<HexPositionPointy> adjacent = new HashSet<>();
        adjacent.add(adjacent(Direction.West));
        adjacent.add(adjacent(Direction.East));
        adjacent.add(adjacent(Direction.NorthEast));
        adjacent.add(adjacent(Direction.NorthWest));
        adjacent.add(adjacent(Direction.SouthEast));
        adjacent.add(adjacent(Direction.SouthWest));
        return adjacent;
    }

    public int distance(HexPositionPointy p) {
        return (Math.abs(col - p.col) + Math.abs(col + row - p.col - p.row) + Math.abs(row - p.row)) / 2;
    }
}
