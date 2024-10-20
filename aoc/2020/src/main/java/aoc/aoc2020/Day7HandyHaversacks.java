package aoc.aoc2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class Day7HandyHaversacks {
    final Map<String, Bag> bags = new HashMap<>();

    Day7HandyHaversacks(List<String> inputLines) {
        for (String line : inputLines) {
            String[] firstSplit = line.split(" bags contain ");
            String color = firstSplit[0];
            String[] contentList = firstSplit[1].split(",");

            Bag bag;
            if (bags.containsKey(color)) {
                bag = bags.get(color);
            } else {
                bag = new Bag(color);
                bags.put(color, bag);
            }

            var pattern = Pattern.compile("(\\d+) (\\w+ \\w+) bag.*");
            for (String content : contentList) {
                var matcher = pattern.matcher(content);
                if (matcher.find()) {
                    int amount = Integer.parseInt(matcher.group(1));
                    String secondColor = matcher.group(2);

                    Bag secondBag;
                    if (bags.containsKey(secondColor)) {
                        secondBag = bags.get(secondColor);
                    } else {
                        secondBag = new Bag(secondColor);
                        bags.put(secondColor, secondBag);
                    }
                    bag.content.put(secondBag, amount);
                }
            }
        }
    }

    long problem1() {
        return bags.values().stream().filter(Bag::containsGoldBag).count() - 1;
    }

    long problem2() {
        return bags.get("shiny gold").numberOfBagsInside() - 1;
    }

    static class Bag {
        final String color;
        final Map<Bag, Integer> content = new HashMap<>();

        public Bag(String color) {
            this.color = color;
        }

        boolean containsGoldBag() {
            if (color.equals("shiny gold")) {
                return true;
            } else if (content.isEmpty()) {
                return false;
            } else {
                boolean found = false;
                for (var bag : content.keySet()) {
                    boolean res = bag.containsGoldBag();
                    if (res) {
                        found = true;
                    }
                }
                return found;
            }
        }

        int numberOfBagsInside() {
            int numberOfBags = 1;
            for (var bag : content.keySet()) {
                numberOfBags += content.get(bag) * bag.numberOfBagsInside();
            }
            return numberOfBags;
        }
    }
}
