package aoc.aoc2016;

import aoc.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8TwoFactorAuthentication {

    @Data
    @AllArgsConstructor
    static class Pixel {
        boolean lit;
        //Position pos;
    }

    @Data
    static class Screen {
        final int screenWidth;
        final int screenHeight;

        HashMap<Position, Pixel> screen = new HashMap<>();

        Screen(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Position p = new Position(x, y);
                    //screen.put(p, new Pixel(false, p));
                    screen.put(p, new Pixel(false));
                }
            }
        }

        void rect(int width, int height) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    screen.get(new Position(x, y)).setLit(true);
                }
            }
        }

        void rotateRow(int row, int distance) {
            HashMap<Position, Pixel> newRow = new HashMap<>();

            for (int x = 0; x < screenWidth; x++) {
                int dstCol = (x + distance < screenWidth) ? x + distance : x + distance - screenWidth;
                Position src = new Position(x, row);
                Position dst = new Position(dstCol, row);
                newRow.put(dst, screen.get(src));
            }

            for (Position pos : newRow.keySet()) {
                screen.put(pos, newRow.get(pos));
            }
        }

        void rotateColumn(int column, int distance) {
            HashMap<Position, Pixel> newColumn = new HashMap<>();

            for (int y = 0; y < screenHeight; y++) {
                int dstRow = (y + distance < screenHeight) ? y + distance : y + distance - screenHeight;
                Position src = new Position(column, y);
                Position dst = new Position(column, dstRow);
                newColumn.put(dst, screen.get(src));
            }

            for (Position pos : newColumn.keySet()) {
                screen.put(pos, newColumn.get(pos));
            }
        }

        long litPixels() {
            return screen.values().stream().filter(Pixel::isLit).count();
        }

        void print() {
            for (int y = 0; y < screenHeight; y++) {
                for (int x = 0; x < screenWidth; x++) {
                    if (screen.get(new Position(x, y)).isLit()) {
                        System.out.print('#');
                    } else {
                        System.out.print('.');
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }


    private Screen screen;
    private List<String> instructions;


    public Day8TwoFactorAuthentication(int screenWidth, int screenHeight, String fileName) throws IOException {
        readData(fileName);
        screen = new Screen(screenWidth, screenHeight);
    }

    private void readData(String fileName) throws IOException {
        instructions = Files.readAllLines(Paths.get(fileName));
    }

    long pixelsLit() {
        Pattern rectPattern = Pattern.compile("^rect (\\d+)x(\\d+)$");
        Pattern rotatePattern = Pattern.compile("^rotate (row|column) [xy]=(\\d+) by (\\d+)$");

        for (String row : instructions) {
            Matcher matcher = rectPattern.matcher(row);
            if (matcher.find()) {
                screen.rect(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
            }

            matcher = rotatePattern.matcher(row);
            if (matcher.find()) {
                if (matcher.group(1).equals("row")) {
                    screen.rotateRow(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                } else {
                    screen.rotateColumn(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                }
            }
        }
        screen.print();
        return screen.litPixels();
    }
}
