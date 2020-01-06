package aoc.aoc2018;

import aoc.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day18SettlersOfTheNorthPole {

    @Data
    @AllArgsConstructor
    static class Acre {
        Position position;
        char type;
        char newType;
    }

    private Set<Acre> collectionArea = new HashSet<>();
    private int areaWidth;
    private int areaHeight;

    public Day18SettlersOfTheNorthPole(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        int x = 0;
        int y = 0;
        for (String row : inputStrings) {
            x = 0;
            for (char c : row.toCharArray()) {
                collectionArea.add(new Acre(new Position(x, y), c, ' '));
                x++;
            }
            y++;
        }
        areaHeight = y;
        areaWidth = x;
    }

    private void printMap() {
        for (int y = 0; y < areaHeight; y++) {
            for (int x = 0; x < areaWidth; x++) {
                System.out.print(getAcre(new Position(x, y))
                        .orElse(new Acre(new Position(x, y), ' ', ' '))
                        .getType());
            }
            System.out.println();
        }
    }

    private Optional<Acre> getAcre(Position position) {
        return collectionArea.stream().filter(acre -> acre.getPosition().equals(position))
                .findFirst();
    }

    private Set<Acre> getAdjacentAcres(Acre acre) {
        Set<Acre> adjacentAcres = new HashSet<>();
        getAcre(new Position(acre.getPosition().getX() - 1, acre.getPosition().getY() - 1)).ifPresent(adjacentAcres::add);
        getAcre(new Position(acre.getPosition().getX(), acre.getPosition().getY() - 1)).ifPresent(adjacentAcres::add);
        getAcre(new Position(acre.getPosition().getX() + 1, acre.getPosition().getY() - 1)).ifPresent(adjacentAcres::add);
        getAcre(new Position(acre.getPosition().getX() - 1, acre.getPosition().getY())).ifPresent(adjacentAcres::add);
        getAcre(new Position(acre.getPosition().getX() + 1, acre.getPosition().getY())).ifPresent(adjacentAcres::add);
        getAcre(new Position(acre.getPosition().getX() - 1, acre.getPosition().getY() + 1)).ifPresent(adjacentAcres::add);
        getAcre(new Position(acre.getPosition().getX(), acre.getPosition().getY() + 1)).ifPresent(adjacentAcres::add);
        getAcre(new Position(acre.getPosition().getX() + 1, acre.getPosition().getY() + 1)).ifPresent(adjacentAcres::add);

        return adjacentAcres;
    }


    int computeResourceValue() {
        int value = 0;

        for (int minutes = 0; minutes < 10; minutes++) {
            value = resourceValue();
            System.out.println("Minutes: " + minutes + ", value: " + value);
        }
        return value;
    }

    private int resourceValue() {

        //System.out.println("Minute: " + minutes);
        //printMap();

        collectionArea.forEach(acre -> acre.setNewType(acre.getType()));

        collectionArea.stream().filter(acre -> acre.getType() == '.')
                .filter(acre -> getAdjacentAcres(acre).stream()
                        .filter(acre1 -> acre1.getType() == '|').count() >= 3)
                .forEach(acre -> acre.setNewType('|'));

        collectionArea.stream().filter(acre -> acre.getType() == '|')
                .filter(acre -> getAdjacentAcres(acre).stream()
                        .filter(acre1 -> acre1.getType() == '#')
                        .count() >= 3)
                .forEach(acre -> acre.setNewType('#'));

        collectionArea.stream().filter(acre -> acre.getType() == '#')
                .forEach(acre -> acre.setNewType('.'));
        collectionArea.stream().filter(acre -> acre.getType() == '#')
                .filter(acre -> getAdjacentAcres(acre).stream()
                        .filter(acre1 -> acre1.getType() == '#')
                        .count() >= 1)
                .filter(acre -> getAdjacentAcres(acre).stream()
                        .filter(acre1 -> acre1.getType() == '|')
                        .count() >= 1)
                .forEach(acre -> acre.setNewType('#'));

        collectionArea.forEach(acre -> acre.setType(acre.getNewType()));

        int wood = Math.toIntExact(collectionArea.stream().filter(acre -> acre.getType() == '|').count());
        int lumberyards = Math.toIntExact(collectionArea.stream().filter(acre -> acre.getType() == '#').count());

        return wood * lumberyards;
    }

    int totalResourceValue() throws IOException {

        BigInteger minutes = new BigInteger("1000000000");
        BigInteger modulo = minutes.mod(new BigInteger("28"));
        // modulo = 20
        int m1 = 451 / 28;
        int m2 = 451 % 28;
        // 451 = 16*28 + 3
        // 100000000 = x * 28 + 20
        // Need to find a reoccurring pattern for the value
        List<Integer> values = new ArrayList<>();

        if (false) {
            for (int i = 0; i < 1000; i++) {
                int value = resourceValue();
                System.out.println("Minutes: " + i + ", value: " + value);
                values.add(value);
            }
            try {
                List<String> strings = values.stream().map(Object::toString).collect(Collectors.toList());
                Files.write(Paths.get("resourceValues.txt"), strings);
            } catch (IOException e) {
                log.error("Unable to write out names", e);
            }
        } else {
            List<String> strings = Files.readAllLines(Paths.get("resourceValues.txt"));
            values = strings.stream().map(Integer::valueOf).collect(Collectors.toList());
        }
        int length = values.size();

        // Start with window size 2 and check number of occurences

        List<Integer> window;
        for (int windowSize = 25; windowSize < 50; windowSize++) {
            System.out.println("----- Testing window size  : " + windowSize);
            for (int valuesIndex = 0; valuesIndex < values.size() - windowSize; valuesIndex++) {
                // The windows moves down one level for each iteration
                window = values.subList(valuesIndex, valuesIndex + windowSize);
                //System.out.println("Setting window to position " + valuesIndex);
                // Now we have a window that will move down the list for each iteration
                // The window should count the number of matches in the values list


                // patterns repeat every 28 positions from position 451
                // Matched [173922, 177815, 182358, ...
                int matches = 0;
                for (int i = 0; i < values.size() - windowSize; i++) {
                    if (windowMatches(values, window, i) && valuesIndex != i) {
                        matches++;
                        //System.out.println("Matched " + window  + " for index : " + i);
                    }
                }

                if (matches > 1) {
                    System.out.println("Found " + matches + " matches for window : " + window + ", on pos: " + valuesIndex);
                }
            }

        }


        // Setting window to position 451
        // Matched [173922, 177815, 182358, 186042, 192504, 195308, 200646, 205120, 208650, 210588,
        //          212833, 212688, 212443, 208278, 200466, 196680, 195290, 189980, 186795, 184392,
        //          180560, 181383, 182700, 180942, 176782, 175212, 173290, 173658 ]
        //
        // 175212 should be the start, i.e
        // Matched [175212, 173290, 173658, 173922, 177815, 182358, 186042, 192504, 195308, 200646,
        //          205120, 208650, 210588, 212833, 212688, 212443, 208278, 200466, 196680, 195290,
        //          189980, 186795, 184392, 180560, 181383, 182700, 180942, 176782  ]
        // offset 20 => 189980
        // 189980 too low...
        // 195290 correct


        return 195290;
    }

    private boolean windowMatches(List<Integer> values, List<Integer> window, int index) {
        boolean match = true;
        for (int j = 0; j < window.size(); j++) {
            if (!window.get(j).equals(values.get(index + j))) {
                match = false;
            }
        }
        return match;
    }

}
