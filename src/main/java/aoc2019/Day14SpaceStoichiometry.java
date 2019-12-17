package aoc2019;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.Fraction;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day14SpaceStoichiometry {

    @Data
    @AllArgsConstructor
    class Chemical {
        String name;
    }

    @Data
    @AllArgsConstructor
    class Mix {
        Chemical chemical;
        int amount;
    }

    @Data
    @AllArgsConstructor
    class ReactionResult {
        int amount;
        int divisor;
    }

    // Example
    // reactionResults<A, 28

    @Data
    @AllArgsConstructor
    class Reaction {
        String name;
        List<Mix> inputs;
        Mix output;

        boolean isReduceable() {
            boolean result = true;
            for (Mix mix : inputs) {
                if (!mix.getChemical().equals(ore)) {
                    result = false;
                    break;
                }
            }
            return result;
        }

        boolean inputContains(Chemical c) {
            boolean result = false;
            for (Mix mix : inputs) {
                if (mix.getChemical().equals(c)) {
                    result = true;
                    break;
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return "Reaction{" +
                    "name='" + name + '\'' +
                    '}';
        }

        // recursive
        void doReaction(Reaction reaction) {
            log.info("doReaction for {}", reaction);
            for (Mix mix : reaction.inputs) {
                Chemical chemical = mix.getChemical();
                if (chemical.equals(ore)) {
                    if (reactionResults.containsKey(chemical)) {
                        reactionResults.get(chemical).amount += mix.getAmount();
                    } else {
                        //reactionResults.put(chemical, new ReactionResult(mix.getAmount(), reaction.))
                    }
                    //result.add(Fraction.getFraction(mix.getAmount(), reaction.output.amount));
                } else {
                    // Which reaction generates chemical?
                    Reaction re = reactions.stream().filter(r -> r.output.chemical.equals(chemical)).findFirst().orElse(null);
                    //result.add(doReaction(re).multiplyBy(Fraction.getFraction(mix.getAmount(), reaction.output.amount)));
                }
            }
            //log.info("doReaction returning {} for {}", result, reaction);
            return;
        }
    }

    Set<Chemical> chemicals = new HashSet<>();
    Set<Reaction> reactions = new HashSet<>();
    Map<Chemical, ReactionResult> reactionResults = new HashMap<>();
    Chemical ore;
    Chemical fuel;

    public Day14SpaceStoichiometry(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^(((\\d+) (\\w+),? )+)=> (?<outputAmount>\\d+) (?<outputChemical>\\w+)$");
        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                List<Mix> inputs = new ArrayList<>();
                String indata = matcher.group(1);
                for (String quantity : indata.split(",")) {
                    String[] split = quantity.trim().split(" ");
                    Chemical inputChemical = new Chemical(split[1]);
                    chemicals.add(inputChemical);
                    Mix inputQuantity = new Mix(inputChemical, Integer.parseInt(split[0]));
                    inputs.add(inputQuantity);
                    if (inputChemical.name.equals("ORE")) {
                        ore = inputChemical;
                    }
                }
                Chemical outputChemical = new Chemical(matcher.group("outputChemical"));
                chemicals.add(outputChemical);
                if (outputChemical.name.equals("FUEL")) {
                    fuel = outputChemical;
                }
                Mix output = new Mix(outputChemical, Integer.parseInt(matcher.group("outputAmount")));
                reactions.add(new Reaction("To " + outputChemical.name, inputs, output));
            }
        }
    }

    int minimumAmountOfOre() {
        int iterations = 0;

        Reaction start = reactions.stream().filter(r -> r.output.getChemical().name.equals("FUEL")).findFirst().orElse(null);
        start.doReaction(start);

        while (chemicals.size() > 2) {
            log.info("Iteration {}", iterations);

            // find chemicals which reactions can be reduced. The are the ones that only have ore as input
            Map<Chemical, Fraction> reductions = new HashMap<>();
            for (Reaction reaction : reactions) {
                if (reaction.isReduceable()) {
                    Mix mix = reaction.output;
                    reductions.put(mix.chemical, Fraction.getFraction(mix.amount, reaction.output.amount));
                    log.info("Adding {} to reduction list (from reaction {})", mix.chemical.name, reaction);
                }
            }

            // go through all reactions and reduce the found chemicals
            for (Chemical chemicalToReduce : reductions.keySet()) {
                for (Reaction reaction : reactions) {
                    if (reaction.inputContains(chemicalToReduce)) {
                        int denominator = reductions.get(chemicalToReduce).getDenominator();
                        for (Mix mix : reaction.inputs) {
                            if (mix.chemical.equals(chemicalToReduce)) {
                                log.info("Reducing {} on {}", chemicalToReduce, reaction);
                                mix.chemical = ore;
                                mix.amount *= reductions.get(chemicalToReduce).getNumerator();
                            } else {
                                mix.amount *= denominator;
                            }
                        }
                        reaction.output.amount *= denominator;
                        // add all chemicalQuantities that are Ore into one
                        Mix newMix = new Mix(ore, 0);
                        for (Mix mix : reaction.inputs) {
                            if (mix.getChemical().equals(ore)) {
                                newMix.amount += mix.getAmount();
                            }
                        }
                        reaction.inputs.removeIf(cq -> cq.getChemical().equals(ore));
                        reaction.inputs.add(newMix);
                    }
                }
                chemicals.remove(chemicalToReduce);
            }
            iterations++;
        }
        //
        return 0;
    }
}
