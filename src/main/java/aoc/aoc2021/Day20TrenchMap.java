package aoc.aoc2021;

import aoc.Direction;
import aoc.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class Day20TrenchMap {

    String algorithm;
    Set<Position> map = new HashSet<>();

    public Day20TrenchMap(List<String> inputLines) {
        algorithm = inputLines.get(0);


        for (int y = 0; y < inputLines.size(); y++) {
            String line = inputLines.get(y + 2);
            int x = 0;
            for (char c : line.toCharArray()) {
                map.add(new Position(x, y));
                x++;
            }
        }
    }

    int problem1() {
        return 0;
    }

    int problem2() {
        return 0;
    }

    Set<Position> enhance(Set<Position> input) {
        Set<Position> newMap = new HashSet<>();
        Set<Position> checkedPositions = new HashSet<>();
        for (Position p : map) {
            Set<Position> grid = p.allAdjacentIncludingDiagonal();
            for (Position gridPos : grid) {
                if (!checkedPositions.contains(gridPos)) {
                    String binaryNumber = binaryNumber(gridPos);
                }
            }
        }
        return newMap;
    }

    private String binaryNumber(Position pos) {
        StringBuilder sb = new StringBuilder();


         if (map.contains(pos.adjacent(Direction.NorthWest))) {
             sb.append(".");

        }
    }
}
