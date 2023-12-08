package aoc.aoc2023;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Slf4j
public class Day2CubeConundrum {
    final List<Game> games = new ArrayList<>();

    public Day2CubeConundrum(List<String> inputLines) {
        var pattern = Pattern.compile("(\\d+) (\\w+)");

        for (String line : inputLines) {
            var game = new Game();
            String[] s = line.substring(line.indexOf(":") + 1).split(";");
            for (String cubeset : s) {
                String[] s2 = cubeset.split(",");
                var cubeSet = new CubeSet();
                for (String cs : s2) {
                    var matcher = pattern.matcher(cs);
                    if (matcher.find()) {
                        int value = Integer.parseInt(matcher.group(1));
                        switch (matcher.group(2)) {
                            case "blue":
                                cubeSet.blue = value;
                                break;
                            case "red":
                                cubeSet.red = value;
                                break;
                            case "green":
                                cubeSet.green = value;
                                break;
                            default:
                                throw new IllegalArgumentException("Ooopsie");
                        }
                    }
                }
                game.cubeSets.add(cubeSet);
            }
            games.add(game);
        }
    }

    public int problem1() {
        int gameIndex = 1;
        int sum = 0;
        for (var game : games) {
            boolean gameOk = true;
            for (var cubeSet : game.cubeSets) {
                if (cubeSet.red > 12 || cubeSet.green > 13 || cubeSet.blue > 14) {
                    gameOk = false;
                    break;
                }
            }

            if (gameOk) {
                sum += gameIndex;
            }
            gameIndex++;
        }
        return sum;
    }

    public int problem2() {
        int sum = 0;
        for (var game : games) {
            int maxRed = 0;
            int maxBlue = 0;
            int maxGreen = 0;
            for (var cubeSet : game.cubeSets) {
                maxRed = Math.max(maxRed, cubeSet.red);
                maxBlue = Math.max(maxBlue, cubeSet.blue);
                maxGreen = Math.max(maxGreen, cubeSet.green);
            }
            sum += maxRed * maxBlue * maxGreen;
        }
        return sum;
    }

    static class CubeSet {
        int red;
        int green;
        int blue;
    }

    static class Game {
        final Set<CubeSet> cubeSets = new HashSet<>();
    }
}
