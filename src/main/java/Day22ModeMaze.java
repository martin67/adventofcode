import java.util.HashMap;

public class Day22ModeMaze {

    static class Region {
        final Position position;
        int geologicIndex;
        int erosionLevel;
        int type;

        Region(Position position) {
            this.position = position;
        }
    }

    private final HashMap<Position, Region> cave = new HashMap<>();
    private final Position target;

    public Day22ModeMaze(int depth, Position target) {
        this.target = new Position(target.x + 1, target.y + 1);
        initCave(depth, this.target);
    }

    private void initCave(int depth, Position target) {

        for (int x = 0; x < target.x; x++) {
            for (int y = 0; y < target.y; y++) {
                Region r = new Region(new Position(x, y));

                if ((x == 0 && y == 0) || ((x == target.x - 1 && y == target.y - 1))) {
                    r.geologicIndex = 0;
                } else if (x == 0) {
                    r.geologicIndex = y * 48271;
                } else if (y == 0) {
                    r.geologicIndex = x * 16807;
                } else {
                    r.geologicIndex = cave.get(new Position(x - 1, y)).erosionLevel *
                            cave.get(new Position(x, y - 1)).erosionLevel;
                }
                r.erosionLevel = (r.geologicIndex + depth) % 20183;
                r.type = r.erosionLevel % 3;
                cave.put(r.position, r);
            }
        }
    }

    private void printCave() {
        for (int y = 0; y < target.y; y++) {
            for (int x = 0; x < target.x; x++) {
                Region r = cave.get(new Position(x, y));
                if (r.type == 0) {
                    System.out.print('.');
                } else if (r.type == 1) {
                    System.out.print('=');
                } else {
                    System.out.print('|');
                }
            }
            System.out.println();
        }
    }

    public int computeRiskLevel() {
        //printCave();

        int riskLevel = 0;
        for (Region r : cave.values()) {
            riskLevel += r.type;
        }
        return riskLevel;
    }

}
