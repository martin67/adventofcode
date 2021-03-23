package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day21AllergenAssessment {
    List<Food> foods = new ArrayList<>();
    Map<String, Ingredient> ingredients = new HashMap<>();
    Map<String, Allergen> allergens = new HashMap<>();
    Set<Ingredient> usedIngredients;

    public Day21AllergenAssessment(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^(.*) \\(contains (.*)\\)$");

        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                Food food = new Food();
                for (String name : matcher.group(1).split(" ")) {
                    Ingredient ingredient;
                    if (ingredients.containsKey(name)) {
                        ;
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
        for (Food food : foods) {
            for (Ingredient ingredient : food.ingredients) {
                for (Allergen allergen : food.allergens) {
                    ingredient.possibleAllergens.putIfAbsent(allergen, 0);
                    ingredient.possibleAllergens.put(allergen, ingredient.possibleAllergens.get(allergen) + 1);
                }
            }
            for (Allergen allergen : food.allergens) {
                for (Ingredient ingredient : food.ingredients) {
                    allergen.possibleIngredients.putIfAbsent(ingredient, 0);
                    allergen.possibleIngredients.put(ingredient, allergen.possibleIngredients.get(ingredient) + 1);
                }
            }
        }
    }

    int problem1() {

        // Check which allergens that have ingredients that are in every food
        usedIngredients = new HashSet<>(ingredients.values());

        for (Allergen allergen : allergens.values()) {
            for (Ingredient ingredient : ingredients.values()) {
                if (allergen.possibleIngredients.containsKey(ingredient) && allergen.inFoods.size() == allergen.possibleIngredients.get(ingredient)) {
                    log.info("Allergen {} exist in all food with ingredient {}", allergen.name, ingredient.name);
                    usedIngredients.remove(ingredient);
                }
            }
        }
        int appears = 0;
        for (Ingredient ingredient : usedIngredients) {
            log.info("Remaining ingredient: {}", ingredient.name);
            for (Food food : foods) {
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

        for (Ingredient ingredient : ingredients.values()) {
            if (!usedIngredients.contains(ingredient)) {
                unusedIngredients.add(ingredient);
            }
        }

        ingredients.clear();
        for (Ingredient ingredient : unusedIngredients) {
            ingredients.put(ingredient.name, ingredient);
        }
        for (Ingredient ingredient : usedIngredients) {
            for (Allergen allergen : allergens.values()) {
                if (allergen.possibleIngredients.containsKey(ingredient)) {
                    allergen.possibleIngredients.remove(ingredient);
                }
            }
        }

        // Check which allergen that only exits in one ingredient
        for (Allergen allergen : allergens.values()) {
            int appear = 0;
            for (Ingredient ingredient : unusedIngredients) {
                if (ingredient.possibleAllergens.containsKey(allergen)) {
                    appear++;
                }
            }
            log.info("Allergen {} exist in {} ingredients", allergen.name, appear);
        }
        return "hej";
    }

    class Food {
        Set<Ingredient> ingredients = new HashSet<>();
        Set<Allergen> allergens = new HashSet<>();
    }

    class Ingredient {
        String name;
        Map<Allergen, Integer> possibleAllergens = new HashMap<>();
        Set<Food> inFoods = new HashSet<>();

        public Ingredient(String name) {
            this.name = name;
        }
    }

    class Allergen {
        String name;
        Map<Ingredient, Integer> possibleIngredients = new HashMap<>();
        Set<Food> inFoods = new HashSet<>();

        public Allergen(String name) {
            this.name = name;
        }
    }

}
