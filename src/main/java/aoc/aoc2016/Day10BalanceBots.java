package aoc.aoc2016;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10BalanceBots {

    @Data
    static class Bot {
        List<Integer> chips = new ArrayList<>();

        Bot(int chip) {
            chips.add(chip);
        }

        void add(int chip) {
            chips.add(chip);
        }

        int lower() {
            return chips.stream().min(Integer::compare).orElse(0);
        }

        int higher() {
            return chips.stream().max(Integer::compare).orElse(0);
        }
    }

    @Data
    @AllArgsConstructor
    static class Destination {
        String type;
        int destination;
    }

    @Data
    @AllArgsConstructor
    static class Instruction {
        int sourceBot;
        Destination low;
        Destination high;
    }

    private final HashMap<Integer, Bot> bots = new HashMap<>();
    private final HashMap<Integer, Integer> outputs = new HashMap<>();
    private final Set<Instruction> instructions = new HashSet<>();

    public Day10BalanceBots(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));
        Pattern initPattern = Pattern.compile("^value (\\d+) goes to bot (\\d+)$");
        Pattern instructionPattern = Pattern.compile("^bot (\\d+) gives low to (bot|output) (\\d+) and high to (bot|output) (\\d+)$");

        for (String row : inputStrings) {
            Matcher matcher = initPattern.matcher(row);
            // value 41 goes to bot 12
            if (matcher.find()) {
                int value = Integer.parseInt(matcher.group(1));
                int botNumber = Integer.parseInt(matcher.group(2));
                if (bots.containsKey(botNumber)) {
                    bots.get(botNumber).add(value);
                } else {
                    bots.put(botNumber, new Bot(value));
                }
            }
            matcher = instructionPattern.matcher(row);
            //bot 113 gives low to bot 127 and high to bot 196
            //bot 169 gives low to output 20 and high to bot 83
            if (matcher.find()) {
                instructions.add(new Instruction(Integer.parseInt(matcher.group(1)),
                        new Destination(matcher.group(2), Integer.parseInt(matcher.group(3))),
                        new Destination(matcher.group(4), Integer.parseInt(matcher.group(5)))));
            }
        }
    }

    private void runFactory() {

        boolean done = false;
        while (!done) {
            for (Iterator<Instruction> i = instructions.iterator(); i.hasNext(); ) {
                Instruction instruction = i.next();
                // Find sourceBots that have both chips available
                if (bots.containsKey(instruction.sourceBot) && bots.get(instruction.sourceBot).chips.size() == 2) {
                    Bot sourceBot = bots.get(instruction.sourceBot);
                    Destination lowDestination = instruction.low;
                    Destination highDestination = instruction.high;

                    if (lowDestination.type.equals("bot")) {
                        if (bots.containsKey(lowDestination.destination)) {
                            bots.get(lowDestination.destination).add(sourceBot.lower());
                        } else {
                            bots.put(lowDestination.destination, new Bot(sourceBot.lower()));
                        }
                    } else {
                        outputs.put(lowDestination.destination, sourceBot.lower());
                    }

                    if (highDestination.type.equals("bot")) {
                        if (bots.containsKey(highDestination.destination)) {
                            bots.get(highDestination.destination).add(sourceBot.higher());
                        } else {
                            bots.put(highDestination.destination, new Bot(sourceBot.higher()));
                        }
                    } else {
                        outputs.put(highDestination.destination, sourceBot.higher());
                    }

                    i.remove();
                }
            }

            if (instructions.isEmpty()) {
                done = true;
            }
        }
    }

    int botNumber(int compareOne, int compareTwo) {
        runFactory();
        List<Integer> target = Arrays.asList(compareOne, compareTwo);
        return bots.entrySet().stream()
                .filter(e -> new HashSet<>(e.getValue().chips).containsAll(target))
                .findFirst().map(Map.Entry::getKey).get();
    }

    int multiplyOutput() {
        runFactory();
        return outputs.get(0) * outputs.get(1) * outputs.get(2);
    }
}
