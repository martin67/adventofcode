import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

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

    public Day18SettlersOfTheNorthPole(String input) {
        readData(input);
    }

    private void readData(String input) {
        List<String> inputStrings = Arrays.stream(input.split("\\n+"))
                .collect(Collectors.toList());

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

    Set<Acre> getAdjacentAcres(Acre acre) {
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


        for (int minutes = 0; minutes < 10; minutes++) {
            System.out.println("Minute: " + minutes);
            printMap();

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
        }
        int wood = Math.toIntExact(collectionArea.stream().filter(acre -> acre.getType() == '|').count());
        int lumberyards = Math.toIntExact(collectionArea.stream().filter(acre -> acre.getType() == '#').count());
        return wood * lumberyards;
    }
}
