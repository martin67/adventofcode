package aoc.aoc2022;

import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day8TreetopTreeHouse {

    final Map<Position, Integer> trees = new HashMap<>();
    final Map<Position, Integer> viewingDistances = new HashMap<>();
    int width;
    final int height;

    Day8TreetopTreeHouse(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (Character c : line.toCharArray()) {
                trees.put(new Position(x, y), Integer.parseInt(c.toString()));
                x++;
                width = x;
            }
            y++;
        }
        height = y;
    }

    int problem1() {
        return compute();
    }

    int problem2() {
        compute();
        return viewingDistances.values().stream().max(Comparator.naturalOrder()).orElseThrow();
    }

    int compute() {
        // add borders
        int visible = width * 2 + height * 2 - 4;

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                var position = new Position(x, y);
                int treeHeight = trees.get(position);
                int viewingDistance = 1;
                boolean isVisibleInOneDirection = false;
                boolean isVisible = true;

                // up
                int dY = y - 1;
                while (dY >= 0) {
                    if (trees.get(new Position(x, dY)) >= treeHeight) {
                        isVisible = false;
                        break;
                    } else {
                        viewingDistance++;
                    }
                    dY--;
                }
                if (isVisible) {
                    viewingDistance--;
                    isVisibleInOneDirection = true;
                }
                //log.info("Pos: {}, direction up, viewing distance: {}, visible: {}", p, viewingDistance, isVisible);
                viewingDistances.put(position, viewingDistance);

                // down
                isVisible = true;
                viewingDistance = 1;
                dY = y + 1;
                while (dY < height) {
                    if (trees.get(new Position(x, dY)) >= treeHeight) {
                        isVisible = false;
                        break;
                    } else {
                        viewingDistance++;
                    }
                    dY++;
                }
                if (isVisible) {
                    viewingDistance--;
                    isVisibleInOneDirection = true;
                }
                //log.info("Pos: {}, direction down, viewing distance: {}, visible: {}", p, viewingDistance, isVisible);
                viewingDistances.put(position, viewingDistances.get(position) * viewingDistance);

                // left
                isVisible = true;
                viewingDistance = 1;
                int dX = x - 1;
                while (dX >= 0) {
                    if (trees.get(new Position(dX, y)) >= treeHeight) {
                        isVisible = false;
                        break;
                    } else {
                        viewingDistance++;
                    }
                    dX--;
                }
                if (isVisible) {
                    viewingDistance--;
                    isVisibleInOneDirection = true;
                }
                //log.info("Pos: {}, direction left, viewing distance: {}, visible: {}", p, viewingDistance, isVisible);
                viewingDistances.put(position, viewingDistances.get(position) * viewingDistance);

                // right
                isVisible = true;
                viewingDistance = 1;
                dX = x + 1;
                while (dX < width) {
                    if (trees.get(new Position(dX, y)) >= treeHeight) {
                        isVisible = false;
                        break;
                    } else {
                        viewingDistance++;
                    }
                    dX++;
                }
                if (isVisible) {
                    viewingDistance--;
                    isVisibleInOneDirection = true;
                }
                //log.info("Pos: {}, direction right, viewing distance: {}, visible: {}", p, viewingDistance, isVisible);
                viewingDistances.put(position, viewingDistances.get(position) * viewingDistance);

                if (isVisibleInOneDirection) {
                    visible++;
                }
            }
        }
        return visible;
    }
}
