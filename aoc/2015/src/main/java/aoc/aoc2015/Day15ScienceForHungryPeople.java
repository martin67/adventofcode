package aoc.aoc2015;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day15ScienceForHungryPeople {
    final List<Ingredient> ingredients = new ArrayList<>();

    public Day15ScienceForHungryPeople(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^(\\w+): capacity (-?\\d+), durability (-?\\d+), flavor (-?\\d+), texture (-?\\d+), calories (-?\\d+)$");

        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                ingredients.add(new Ingredient(matcher.group(1),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4)),
                        Integer.parseInt(matcher.group(5)),
                        Integer.parseInt(matcher.group(6))));
            }
        }
    }

    public int totalScore(boolean countCalories) {
        int maxScore = Integer.MIN_VALUE;

        if (ingredients.size() == 2) {
            for (int i = 0; i < 101; i++) {
                int capacity = ingredients.get(0).capacity * i + ingredients.get(1).capacity * (100 - i);
                int durability = ingredients.get(0).durability * i + ingredients.get(1).durability * (100 - i);
                int flavor = ingredients.get(0).flavor * i + ingredients.get(1).flavor * (100 - i);
                int texture = ingredients.get(0).texture * i + ingredients.get(1).texture * (100 - i);
                int calories = ingredients.get(0).calories * i + ingredients.get(1).calories * (100 - i);
                if (capacity < 0) {
                    capacity = 0;
                }
                if (durability < 0) {
                    durability = 0;
                }
                if (flavor < 0) {
                    flavor = 0;
                }
                if (texture < 0) {
                    texture = 0;
                }
                int score = capacity * durability * flavor * texture;
                if (countCalories) {
                    if (score > maxScore && calories == 500) {
                        maxScore = score;
                    }
                } else {
                    if (score > maxScore) {
                        maxScore = score;
                    }
                }
            }
        } else if (ingredients.size() == 4) {
            for (int i = 0; i < 101; i++) {
                for (int j = 0; j < 101; j++) {
                    for (int k = 0; k < 101; k++) {
                        for (int l = 0; l < 101; l++) {
                            if (i + j + k + l == 100) {
                                int capacity = ingredients.get(0).capacity * i +
                                        ingredients.get(1).capacity * j +
                                        ingredients.get(2).capacity * k +
                                        ingredients.get(3).capacity * l;
                                int durability = ingredients.get(0).durability * i +
                                        ingredients.get(1).durability * j +
                                        ingredients.get(2).durability * k +
                                        ingredients.get(3).durability * l;
                                int flavor = ingredients.get(0).flavor * i +
                                        ingredients.get(1).flavor * j +
                                        ingredients.get(2).flavor * k +
                                        ingredients.get(3).flavor * l;
                                int texture = ingredients.get(0).texture * i +
                                        ingredients.get(1).texture * j +
                                        ingredients.get(2).texture * k +
                                        ingredients.get(3).texture * l;
                                int calories = ingredients.get(0).calories * i +
                                        ingredients.get(1).calories * j +
                                        ingredients.get(2).calories * k +
                                        ingredients.get(3).calories * l;
                                if (capacity < 0) {
                                    capacity = 0;
                                }
                                if (durability < 0) {
                                    durability = 0;
                                }
                                if (flavor < 0) {
                                    flavor = 0;
                                }
                                if (texture < 0) {
                                    texture = 0;
                                }
                                int score = capacity * durability * flavor * texture;
                                if (countCalories) {
                                    if (score > maxScore && calories == 500) {
                                        maxScore = score;
                                    }
                                } else {
                                    if (score > maxScore) {
                                        maxScore = score;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return maxScore;
    }

    record Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {
    }
}
