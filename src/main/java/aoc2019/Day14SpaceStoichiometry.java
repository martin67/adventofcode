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
    class ChemicalQuantity {
        Chemical chemical;
        int amount;
    }

    @Data
    @AllArgsConstructor
    class Reaction {
        String name;
        List<ChemicalQuantity> inputs;
        ChemicalQuantity output;

        boolean isReduceable() {
            boolean result = true;
            for (ChemicalQuantity chemicalQuantity : inputs) {
                if (!chemicalQuantity.getChemical().equals(ore)) {
                    result = false;
                    break;
                }
            }
            return result;
        }

        boolean inputContains(Chemical c) {
            boolean result = false;
            for (ChemicalQuantity cq : inputs) {
                if (cq.getChemical().equals(c)) {
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

        // returns Fractions of ore
        Fraction doReaction(Reaction reaction) {
            log.info("doReaction for {}", reaction);
            Fraction result = Fraction.ONE;
            for (ChemicalQuantity cq : reaction.inputs) {
                Chemical cqi = cq.getChemical();
                if (cqi.equals(ore)) {
                    result.add(Fraction.getFraction(cq.getAmount(), reaction.output.amount));
                } else {
                    // Which reaction generates cqi?
                    Reaction re = reactions.stream().filter(r -> r.output.chemical.equals(cqi)).findFirst().orElse(null);
                    result.add(doReaction(re).multiplyBy(Fraction.getFraction(cq.getAmount(), reaction.output.amount)));
                }
            }
            log.info("doReaction returning {} for {}", result, reaction);
            return result;
        }
    }

    Set<Chemical> chemicals = new HashSet<>();
    Set<Reaction> reactions = new HashSet<>();
    Chemical ore;

    public Day14SpaceStoichiometry(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^(((\\d+) (\\w+),? )+)=> (?<outputAmount>\\d+) (?<outputChemical>\\w+)$");
        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                List<ChemicalQuantity> inputs = new ArrayList<>();
                String indata = matcher.group(1);
                for (String quantity : indata.split(",")) {
                    String[] split = quantity.trim().split(" ");
                    Chemical inputChemical = new Chemical(split[1]);
                    chemicals.add(inputChemical);
                    ChemicalQuantity inputQuantity = new ChemicalQuantity(inputChemical, Integer.parseInt(split[0]));
                    inputs.add(inputQuantity);
                    if (inputChemical.name.equals("ORE")) {
                        ore = inputChemical;
                    }
                }
                Chemical outputChemical = new Chemical(matcher.group("outputChemical"));
                chemicals.add(outputChemical);
                ChemicalQuantity output = new ChemicalQuantity(outputChemical, Integer.parseInt(matcher.group("outputAmount")));
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
                    ChemicalQuantity chemicalQuantity = reaction.output;
                    reductions.put(chemicalQuantity.chemical, Fraction.getFraction(chemicalQuantity.amount, reaction.output.amount));
                    log.info("Adding {} to reduction list (from reaction {})", chemicalQuantity.chemical.name, reaction);
                }
            }

            // go through all reactions and reduce the found chemicals
            for (Chemical chemicalToReduce : reductions.keySet()) {
                for (Reaction reaction : reactions) {
                    if (reaction.inputContains(chemicalToReduce)) {
                        int denominator = reductions.get(chemicalToReduce).getDenominator();
                        for (ChemicalQuantity chemicalQuantity : reaction.inputs) {
                            if (chemicalQuantity.chemical.equals(chemicalToReduce)) {
                                log.info("Reducing {} on {}", chemicalToReduce, reaction);
                                chemicalQuantity.chemical = ore;
                                chemicalQuantity.amount *= reductions.get(chemicalToReduce).getNumerator();
                            } else {
                                chemicalQuantity.amount *= denominator;
                            }
                        }
                        reaction.output.amount *= denominator;
                        // add all chemicalQuantities that are Ore into one
                        ChemicalQuantity newChemicalQuantity = new ChemicalQuantity(ore, 0);
                        for (ChemicalQuantity chemicalQuantity : reaction.inputs) {
                            if (chemicalQuantity.getChemical().equals(ore)) {
                                newChemicalQuantity.amount += chemicalQuantity.getAmount();
                            }
                        }
                        reaction.inputs.removeIf(cq -> cq.getChemical().equals(ore));
                        reaction.inputs.add(newChemicalQuantity);
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
