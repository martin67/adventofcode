package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day21AllergenAssessment {
    final List<Food> foods = new ArrayList<>();
    final Map<String, Ingredient> ingredients = new HashMap<>();
    final Map<String, Allergen> allergens = new HashMap<>();
    Set<Ingredient> usedIngredients;

    Day21AllergenAssessment(List<String> inputLines) {
        var pattern = Pattern.compile("^(.*) \\(contains (.*)\\)$");

        for (String line : inputLines) {
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                var food = new Food();
                for (String name : matcher.group(1).split(" ")) {
                    Ingredient ingredient;
                    if (ingredients.containsKey(name)) {
                        ingredient = ingredients.get(name);
                    } else {
                        ingredient = new Ingredient(name);
                        ingredients.put(name, ingredient);
                    }
                    ingredient.inFoods.add(food);
                    food.ingredients.add(ingredient);
                }
                for (String name : matcher.group(2).split(",[ ]*")) {
                    Allergen allergen;
                    if (allergens.containsKey(name)) {
                        allergen = allergens.get(name);
                    } else {
                        allergen = new Allergen(name);
                        allergens.put(name, allergen);
                    }
                    allergen.inFoods.add(food);
                    food.allergens.add(allergen);
                }
                foods.add(food);
            }
        }
        for (var food : foods) {
            for (var ingredient : food.ingredients) {
                for (Allergen allergen : food.allergens) {
                    ingredient.possibleAllergens.putIfAbsent(allergen, 0);
                    ingredient.possibleAllergens.put(allergen, ingredient.possibleAllergens.get(allergen) + 1);
                }
            }
            for (var allergen : food.allergens) {
                for (var ingredient : food.ingredients) {
                    allergen.possibleIngredients.putIfAbsent(ingredient, 0);
                    allergen.possibleIngredients.put(ingredient, allergen.possibleIngredients.get(ingredient) + 1);
                }
            }
        }
    }

    int problem1() {
        // Check which allergens that have ingredients that are in every food
        usedIngredients = new HashSet<>(ingredients.values());

        for (var allergen : allergens.values()) {
            for (var ingredient : ingredients.values()) {
                if (allergen.possibleIngredients.containsKey(ingredient) && allergen.inFoods.size() == allergen.possibleIngredients.get(ingredient)) {
                    log.debug("Allergen {} exist in all food with ingredient {}", allergen.name, ingredient.name);
                    usedIngredients.remove(ingredient);
                }
            }
        }
        int appears = 0;
        for (var ingredient : usedIngredients) {
            log.debug("Remaining ingredient: {}", ingredient.name);
            for (var food : foods) {
                if (food.ingredients.contains(ingredient)) {
                    appears++;
                }
            }
        }
        return appears;
    }

    String problem2() {
        problem1();
        // Remove all unused ingredients
        Set<Ingredient> unusedIngredients = new HashSet<>();
        Map<Allergen, Ingredient> dangerousList = new HashMap<>();

        for (var ingredient : ingredients.values()) {
            if (!usedIngredients.contains(ingredient)) {
                unusedIngredients.add(ingredient);
            }
        }

        ingredients.clear();
        for (var ingredient : unusedIngredients) {
            ingredients.put(ingredient.name, ingredient);
        }
        for (var ingredient : usedIngredients) {
            for (var allergen : allergens.values()) {
                allergen.possibleIngredients.remove(ingredient);
            }
        }

        Ingredient foundIngredient = null;
        Allergen foundAllergen = null;

        while (!allergens.isEmpty()) {
            // Loop through all allergens and
            for (var allergen : allergens.values()) {
                Map<Ingredient, Integer> ingredientFrequency = new HashMap<>();
                int numberOfFoods = 0;
                // See how many times each ingredient is present
                for (var food : foods) {
                    if (food.allergens.contains(allergen)) {
                        numberOfFoods++;
                        for (var ingredient : food.ingredients) {
                            ingredientFrequency.putIfAbsent(ingredient, 0);
                            ingredientFrequency.put(ingredient, ingredientFrequency.get(ingredient) + 1);
                        }
                    }
                }
                // Check if there is one and only one ingredient that exist in all foods found
                int numberFound = 0;
                for (var ingredient : ingredientFrequency.keySet()) {
                    if (ingredientFrequency.get(ingredient) == numberOfFoods) {
                        numberFound++;
                        foundIngredient = ingredient;
                        foundAllergen = allergen;
                        log.debug("Allergen {} exist in all food with ingredient {} ({} times)", allergen, ingredient, numberOfFoods);
                    }
                }
                if (numberFound == 1 && numberOfFoods > 1 || numberFound == 1 && allergens.size() == 1) {
                    log.info("*** Allergen {} exist in all food with ingredient {} ({} times)", allergen, foundIngredient, numberOfFoods);
                    dangerousList.put(allergen, foundIngredient);
                    break;
                }
            }

            // Remove found allergen and ingredient from all lists
            log.debug("Removing {} and {}", foundIngredient, foundAllergen);

            for (var ingredient : ingredients.values()) {
                ingredient.possibleAllergens.remove(foundAllergen);
            }
            for (var allergen : allergens.values()) {
                allergen.possibleIngredients.remove(foundIngredient);
            }
            for (var food : foods) {
                food.ingredients.remove(foundIngredient);
                food.allergens.remove(foundAllergen);
            }
            ingredients.remove(foundIngredient.name);
            allergens.remove(foundAllergen.name);

        }

        // now there should only be one allergen and one ingredient left.
        return dangerousList.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Allergen::getName)))
                .map(Map.Entry::getValue).map(Ingredient::getName).collect(Collectors.joining(","));
    }

    class Food {
        final Set<Ingredient> ingredients = new HashSet<>();
        final Set<Allergen> allergens = new HashSet<>();
    }

    class Ingredient {
        final String name;
        final Map<Allergen, Integer> possibleAllergens = new HashMap<>();
        final Set<Food> inFoods = new HashSet<>();

        public Ingredient(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getName() {
            return name;
        }
    }

    class Allergen {
        final String name;
        final Map<Ingredient, Integer> possibleIngredients = new HashMap<>();
        final Set<Food> inFoods = new HashSet<>();

        public Allergen(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getName() {
            return name;
        }
    }

}
