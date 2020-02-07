package aoc.aoc2019;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day14SpaceStoichiometry {

    @Data
    @AllArgsConstructor
    static class Chemical {
        String name;
    }

    @Data
    @AllArgsConstructor
    static class Mix {
        Chemical chemical;
        int amount;
    }

    @Data
    @AllArgsConstructor
    static class ReactionResult {
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
        void doReaction() {
            log.info("doReaction for {}", this);
            for (Mix mix : inputs) {
                Chemical chemical = mix.getChemical();

                // check if chemical is dependent on ore only
                Reaction inputReaction = reactions.stream()
                        .filter(r -> r.output.chemical.equals(chemical))
                        .findFirst().orElseThrow(NoSuchElementException::new);

                if (inputReaction.inputs.size() == 1 && inputReaction.inputs.get(0).chemical.equals(ore)) {
                    log.info("Adding {} {} to endChemicals", mix.amount, chemical.name);
                    if (endChemicals.containsKey(chemical)) {
                        endChemicals.put(chemical, endChemicals.get(chemical) + mix.amount);
                    } else {
                        endChemicals.put(chemical, mix.amount);
                    }
                } else {
                    for (int i = 0; i < mix.amount; i++) {
                        inputReaction.doReaction();
                    }
                }

                //log.info("doReaction returning {} for {}", result, reaction);
            }
        }
    }

    final Set<Chemical> chemicals = new HashSet<>();
    final Set<Reaction> reactions = new HashSet<>();
    final Map<Chemical, ReactionResult> reactionResults = new HashMap<>();
    Map<Chemical, Integer> endChemicals = new HashMap<>();

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

        Reaction start = reactions.stream()
                .filter(r -> r.output.getChemical().name.equals("FUEL"))
                .findFirst().orElseThrow(NoSuchElementException::new);
        start.doReaction();

        // compute ore needs
        int oreNeeded = 0;
        for (Chemical chemical : endChemicals.keySet()) {
            Reaction oreReaction = reactions.stream()
                    .filter(r -> r.output.chemical.equals(chemical))
                    .findFirst().orElseThrow(NoSuchElementException::new);
            int factor = oreReaction.inputs.get(0).amount;
            int amount = endChemicals.get(chemical);
            int oreFactor = oreReaction.output.amount;

            int temp = ((amount + oreFactor - 1) / oreFactor) * factor;
            log.info("Summing up {}; factor {}, oreFactor {}, amount {}, temp {}", chemical.name, factor, oreFactor, amount, temp);



            //oreNeeded += ((amount + factor - 1) / factor) * factor;
            oreNeeded += temp;
        }
        return oreNeeded;
    }
}

// Plan:
// 2ORE = 2A
// 3ORE = 2B
// 3A,4B => 3C
// 4C => 1 FUEL
//
// 8 ORE => 8A => 4C
// 6 ORE => 4B => 4C
// så 14 ORE för 1 FUEL
//
// då behövs det 4/3 C för 1 FUEL, vilket i sin tur innebär att
// för 1 FUEL behövs det 4/3 * 3/3 A = 12/9 A = 4/3 A och 4/3 * 4/3 B = 16/9 B
// 12/9 A