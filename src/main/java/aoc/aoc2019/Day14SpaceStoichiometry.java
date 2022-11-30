package aoc.aoc2019;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day14SpaceStoichiometry {

    @Data
    @AllArgsConstructor
    static class Recipe {
        Map<String, Long> inputs;
        String chemical;
        long amount;
    }

    final Map<String, Recipe> recipes = new HashMap<>();
    final Map<String, Long> surplus = new HashMap<>();

    public Day14SpaceStoichiometry(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^(((\\d+) (\\w+),? )+)=> (?<outputAmount>\\d+) (?<outputChemical>\\w+)$");
        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                Map<String, Long> inputs = new HashMap<>();
                String indata = matcher.group(1);
                for (String quantity : indata.split(",")) {
                    String[] split = quantity.trim().split(" ");
                    inputs.put(split[1], Long.parseLong(split[0]));
                }
                String output = matcher.group("outputChemical");
                int amount = Integer.parseInt(matcher.group("outputAmount"));
                recipes.put(output, new Recipe(inputs, output, amount));
            }
        }
    }

    long requiredOre(String chemical, long amount) {
        Recipe recipe = recipes.get(chemical);
        long existing = surplus.getOrDefault(chemical, 0L);
        long multiple = (amount - existing + recipe.amount - 1) / recipe.amount;
        long extra = (recipe.amount * multiple) - (amount - existing);
        //log.debug("{}; amount needed: {}, existing: {}, multiple: {}, extra: {}", chemical, amount, existing, multiple, extra);
        if (!recipe.chemical.equals("ORE")) {
            surplus.put(chemical, extra);
        }
        long ore = 0;
        for (String input : recipe.inputs.keySet()) {
            if (input.equals("ORE")) {
                ore += multiple * recipe.inputs.get(input);
            } else
                ore += requiredOre(input, multiple * recipe.inputs.get(input));
        }
        return ore;
    }

    long minimumAmountOfOre() {
        return requiredOre("FUEL", 1);
    }

    long maximumAmountOfFuel() {
        return search(0, Integer.MAX_VALUE);
    }

    long search(long low, long high) {
        if (high - low == 1) {
            log.info("Found it: low {} high {}", low, high);
            return low;
        }
        long mid = ((high - low) / 2) + low;
        surplus.clear();
        long ore = requiredOre("FUEL", mid);
        log.debug("Checking: low {} high {} mid {} ore {}", low, high, mid, ore);
        if (ore > 1000000000000L) {
            return search(low, mid);
        } else {
            return search(mid, high);
        }
    }
}
