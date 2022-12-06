package aoc.aoc2017;

import aoc.common.Position;
import com.google.common.base.Strings;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class Day14DiskDefragmentation {
    private final String key;
    private final Set<Position> squares = new HashSet<>();
    private final Set<Position> squaresCounted = new HashSet<>();

    public Day14DiskDefragmentation(String key) {
        this.key = key;
    }

    int squaresUsed() {
        for (int row = 0; row < 128; row++) {
            String hashKey = key + '-' + row;

            Day10KnotHash day10KnotHash = new Day10KnotHash(256);
            String hash = day10KnotHash.knotHash(hashKey);
            String binaryHash = new BigInteger(hash, 16).toString(2);
            // pad to strength 128 if needed
            if (binaryHash.length() < 128) {
                binaryHash = Strings.padStart(binaryHash, 128, '0');
            }

            for (int col = 0; col < 128; col++) {
                if (binaryHash.charAt(col) == '1') {
                    squares.add(new Position(col, row));
                }
            }
        }
        return squares.size();
    }

    int numberOfRegions() {
        int regions = 0;

        squaresUsed();
        for (Position position : squares) {
            if (!squaresCounted.contains(position)) {
                removeRegion(position);
                regions++;
            }
        }
        return regions;
    }

    void removeRegion(Position pos) {
        squaresCounted.add(pos);
        for (Position adjacent : pos.allAdjacent()) {
            if (squares.contains(adjacent) && !squaresCounted.contains(adjacent)) {
                removeRegion(adjacent);
            }
        }
    }
}
