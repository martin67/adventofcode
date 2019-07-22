import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day14ChocolateCharts {

    @Data
    @AllArgsConstructor
    class Recipe {
        int value;
    }

    @Data
    @AllArgsConstructor
    class Elf {
        int recipeIndex;
    }

    List<Recipe> recipes;
    List<Elf> elves;


    public Day14ChocolateCharts() {
        this.recipes = new ArrayList<>();
        this.elves = new ArrayList<>();
        recipes.add(new Recipe(3));
        recipes.add(new Recipe(7));
        elves.add(new Elf(0));
        elves.add(new Elf(1));
    }

    void print() {
        recipes.stream().forEach(r -> {
            System.out.printf(" %d ", r.getValue());
        });
        System.out.println();
        System.out.println(elves);
    }

    String computeScore(int numberOfRecipes) {
        //print();

        for (int round = 0; round < numberOfRecipes+10; round++) {

            // Compute new recipe
            int newRecipe = 0;
            for (Elf elf : elves) {
                newRecipe += recipes.get(elf.getRecipeIndex()).value;
            }
            //log.info("New recipe: " + newRecipe);

            // add it to the recipe list
            String newRecipeString = String.valueOf(newRecipe);
            for (char c : newRecipeString.toCharArray()) {
                recipes.add(new Recipe(Character.getNumericValue(c)));
            }

            // Move elves
            for (Elf elf : elves) {
                elf.setRecipeIndex(elf.getRecipeIndex() + recipes.get(elf.getRecipeIndex()).value + 1);
                while (elf.getRecipeIndex() >= recipes.size()) {
                    elf.setRecipeIndex(elf.getRecipeIndex() - recipes.size());
                }
            }

            //print();
        }

        // get scores for 10 recipes
        StringBuilder score = new StringBuilder();
        for (int i = numberOfRecipes; i < numberOfRecipes+10; i++) {
            score.append(String.valueOf(recipes.get(i).value));
        }
        return score.toString();
    }

    int computeRecipes(String score) {

        List<Recipe> scoreList = new ArrayList<>();
        for(char c : score.toCharArray()) {
            scoreList.add(new Recipe(Character.getNumericValue(c)));
        }
        boolean foundMatch = false;
        int numberOfRecipes = 2;

        while (!foundMatch) {

            // Compute new recipe
            int newRecipe = 0;
            for (Elf elf : elves) {
                newRecipe += recipes.get(elf.getRecipeIndex()).value;
            }
            //log.info("New recipe: " + newRecipe);

            // add it to the recipe list
            String newRecipeString = String.valueOf(newRecipe);
            for (char c : newRecipeString.toCharArray()) {
                recipes.add(new Recipe(Character.getNumericValue(c)));
                numberOfRecipes++;

                // Check if score is found in the recipe
                // Just need to check the last part of the recipe
                // convert the last parts of recipe to string

                // Skip checks until recipes is long enough
                if (recipes.size() >= score.length()) {
                    // Convert the recipes to a string
                    StringBuilder recipe = new StringBuilder();
                    for (int i = 0; i < score.length(); i++) {
                        recipe.append(recipes.get(
                                recipes.size() - score.length() + i
                        ).getValue());

                    }
                    //log.info("Checking for " + score + " in " + recipe);
                    if (recipe.toString().contains(score)) {
                        log.info("Found match in " + numberOfRecipes);
                        foundMatch = true;
                        break;
                    }
                }

            }

            // Move elves
            for (Elf elf : elves) {
                elf.setRecipeIndex(elf.getRecipeIndex() + recipes.get(elf.getRecipeIndex()).value + 1);
                while (elf.getRecipeIndex() >= recipes.size()) {
                    elf.setRecipeIndex(elf.getRecipeIndex() - recipes.size());
                }
            }


            //print();
            if (numberOfRecipes % 10000 == 0) {
                log.info("round: " + numberOfRecipes);
            }
        }

        return numberOfRecipes - score.length();
    }
}
