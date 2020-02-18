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
        Map<String, Integer> inputs;
        String chemical;
        Integer amount;
    }

    Map<String, Recipe> recipies = new HashMap<>();
    Map<String, Integer> surplus = new HashMap<>();

    public Day14SpaceStoichiometry(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^(((\\d+) (\\w+),? )+)=> (?<outputAmount>\\d+) (?<outputChemical>\\w+)$");
        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                Map<String, Integer> inputs = new HashMap<>();
                String indata = matcher.group(1);
                for (String quantity : indata.split(",")) {
                    String[] split = quantity.trim().split(" ");
                    inputs.put(split[1], Integer.parseInt(split[0]));
                }
                String output = matcher.group("outputChemical");
                int amount = Integer.parseInt(matcher.group("outputAmount"));
                recipies.put(output, new Recipe(inputs, output, amount));
            }
        }
    }

    int requiredOre(String chemical, int amount) {
        Recipe recipe = recipies.get(chemical);
        int existing = surplus.getOrDefault(chemical, 0);
        int multiple = (amount - existing + recipe.amount - 1) / recipe.amount;
        int extra = (recipe.amount * multiple) - (amount - existing);
        log.debug("{}; amount needed: {}, existing: {}, multiple: {}, extra: {}", chemical, amount, existing, multiple, extra);
        if (!recipe.chemical.equals("ORE")) {
            surplus.put(chemical, extra);
        }
        int ore = 0;
        for (String input : recipe.inputs.keySet()) {
            if (input.equals("ORE")) {
                ore += multiple * recipe.inputs.get(input);
            } else
                ore += requiredOre(input, multiple * recipe.inputs.get(input));
        }
        return ore;
    }

    int minimumAmountOfOre() {
        return requiredOre("FUEL", 1);
    }

}
