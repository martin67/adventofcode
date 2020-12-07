package aoc.aoc2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day7HandyHaversacks {

    Map<String, Bag> bags = new HashMap<>();

    public Day7HandyHaversacks(List<String> inputLines) {
        for (String line : inputLines) {
            String[] firstSplit = line.split(" bags contain ");
            String color = firstSplit[0];
            String allContent = firstSplit[1];
            String[] splitContents = allContent.split("(,|\\.)");

            Bag bag;
            if (bags.containsKey(color)) {
                bag = bags.get(color);
            } else {
                bag = new Bag(color);
                bags.put(color, bag);
            }

            Pattern pattern = Pattern.compile("^(\\d+) (\\w+ \\w+) bag(s)?$");
            for (String content : splitContents) {
                Matcher matcher = pattern.matcher(content.trim());
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

    long numberOfBags() {
        return bags.values().stream().filter(Bag::containsGoldBag).count() - 1;
    }

    long numberOfBagsInside() {
        return bags.get("shiny gold").numberOfBagsInside() -1;
    }

    static class Bag {
        String color;
        Map<Bag, Integer> content = new HashMap<>();

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
                for (Bag bag : content.keySet()) {
                    boolean res = bag.containsGoldBag();
                    if (res) {
                        found = true;
                    }
                }
                return found;
            }
        }

        int numberOfBagsInside() {
            if (content.isEmpty()) {
                return 1;
            } else {
                int numberOfBags = 1;
                for (Bag bag : content.keySet()) {
                    numberOfBags += content.get(bag) * bag.numberOfBagsInside();
                }
                return numberOfBags;
            }
        }
    }
}
