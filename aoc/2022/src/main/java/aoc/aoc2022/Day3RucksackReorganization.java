package aoc.aoc2022;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class Day3RucksackReorganization {

    final List<Rucksack> rucksacks = new ArrayList<>();

    public Day3RucksackReorganization(List<String> inputLines) {
        for (String line : inputLines) {
            rucksacks.add(new Rucksack(line));
        }
    }

    int problem1() {
        int priorities = 0;
        for (Rucksack rucksack : rucksacks) {
            Character item = rucksack.findCommonItem();
            priorities += Rucksack.itemScore(item);
        }
        return priorities;
    }


    int problem2() {
        int prioritySum = 0;
        for (int i = 0; i < rucksacks.size(); i = i + 3) {
            Set<Character> items1 = rucksacks.get(i).items();
            Set<Character> items2 = rucksacks.get(i + 1).items();
            Set<Character> items3 = rucksacks.get(i + 2).items();
            items1.retainAll(items2);
            items1.retainAll(items3);
            prioritySum += Rucksack.itemScore(items1.iterator().next());
        }
        return prioritySum;
    }

    static class Rucksack {
        final String contents;

        public Rucksack(String contents) {
            this.contents = contents;
        }

        static int itemScore(Character c) {
            if (c > 96) {
                // lowercase
                return c - 96;
            } else
                // uppercase
                return c - 38;
        }

        Character findCommonItem() {
            Set<Character> compartment1 = new HashSet<>();
            for (Character c : contents.substring(0, contents.length() / 2).toCharArray()) {
                compartment1.add(c);
            }
            for (Character c : contents.substring(contents.length() / 2).toCharArray()) {
                if (compartment1.contains(c)) {
                    return c;
                }
            }
            return null;
        }

        Set<Character> items() {
            Set<Character> items = new HashSet<>();
            for (Character c : contents.toCharArray()) {
                items.add(c);
            }
            return items;
        }
    }
}
