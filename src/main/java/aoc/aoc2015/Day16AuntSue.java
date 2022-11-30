package aoc.aoc2015;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16AuntSue {

    final List<Sue> sues = new ArrayList<>();

    public Day16AuntSue(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^Sue (\\d+): (\\w+): (\\d+), (\\w+): (\\d+), (\\w+): (\\d+)$");

        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                Sue sue = new Sue(Integer.parseInt(matcher.group(1)));
                for (int i = 0; i < 3; i++) {
                    switch (matcher.group(2 + i * 2)) {
                        case "children":
                            sue.children = Integer.parseInt(matcher.group(3 + i * 2));
                            break;
                        case "cats":
                            sue.cats = Integer.parseInt(matcher.group(3 + i * 2));
                            break;
                        case "samoyeds":
                            sue.samoyeds = Integer.parseInt(matcher.group(3 + i * 2));
                            break;
                        case "pomeranians":
                            sue.pomeranians = Integer.parseInt(matcher.group(3 + i * 2));
                            break;
                        case "akitas":
                            sue.akitas = Integer.parseInt(matcher.group(3 + i * 2));
                            break;
                        case "vizslas":
                            sue.vizslas = Integer.parseInt(matcher.group(3 + i * 2));
                            break;
                        case "goldfish":
                            sue.goldfish = Integer.parseInt(matcher.group(3 + i * 2));
                            break;
                        case "trees":
                            sue.trees = Integer.parseInt(matcher.group(3 + i * 2));
                            break;
                        case "cars":
                            sue.cars = Integer.parseInt(matcher.group(3 + i * 2));
                            break;
                        case "perfumes":
                            sue.perfumes = Integer.parseInt(matcher.group(3 + i * 2));
                            break;
                        default:
                            assert false;
                            break;
                    }
                }
                sues.add(sue);
            }
        }
    }

    public int sueNumber() {
        for (Sue sue : sues) {
            if ((sue.children == null || sue.children == 3) &&
                    (sue.cats == null || sue.cats == 7) &&
                    (sue.samoyeds == null || sue.samoyeds == 2) &&
                    (sue.pomeranians == null || sue.pomeranians == 3) &&
                    (sue.akitas == null || sue.akitas == 0) &&
                    (sue.vizslas == null || sue.vizslas == 0) &&
                    (sue.goldfish == null || sue.goldfish == 5) &&
                    (sue.trees == null || sue.trees == 3) &&
                    (sue.cars == null || sue.cars == 2) &&
                    (sue.perfumes == null || sue.perfumes == 1)) {
                return sue.id;
            }
        }
        return 0;
    }

    public int realSueNumber() {
        for (Sue sue : sues) {
            if ((sue.children == null || sue.children == 3) &&
                    (sue.cats == null || sue.cats > 7) &&
                    (sue.samoyeds == null || sue.samoyeds == 2) &&
                    (sue.pomeranians == null || sue.pomeranians < 3) &&
                    (sue.akitas == null || sue.akitas == 0) &&
                    (sue.vizslas == null || sue.vizslas == 0) &&
                    (sue.goldfish == null || sue.goldfish < 5) &&
                    (sue.trees == null || sue.trees > 3) &&
                    (sue.cars == null || sue.cars == 2) &&
                    (sue.perfumes == null || sue.perfumes == 1)) {
                return sue.id;
            }
        }
        return 0;
    }

    static class Sue {
        final int id;
        Integer children;
        Integer cats;
        Integer samoyeds;
        Integer pomeranians;
        Integer akitas;
        Integer vizslas;
        Integer goldfish;
        Integer trees;
        Integer cars;
        Integer perfumes;

        public Sue(int id) {
            this.id = id;
        }
    }

}
