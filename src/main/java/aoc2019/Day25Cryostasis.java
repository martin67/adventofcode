package aoc2019;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day25Cryostasis {

    @Data
    static class AsciiComputer implements Callable<String> {
        ExecutorService executorService;
        private BlockingQueue<BigInteger> inputQueue;
        private BlockingQueue<BigInteger> outputQueue;

        public AsciiComputer(ExecutorService executorService) {
            this.executorService = executorService;
        }

        @Override
        public String call() throws Exception {

            Runnable simpleOutput = () -> {
                while (true) {
                    BigInteger output = null;
                    try {
                        output = inputQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print((char) output.intValue());
                }
            };
            executorService.submit(simpleOutput);

            Runnable simpleInput = () -> {
                Queue<String> commands = new LinkedList<>();
                commands.add("east");
                commands.add("take sand");
                commands.add("west");
                commands.add("south");
                commands.add("take ornament");
                commands.add("north");
                commands.add("west");
                commands.add("north");
                commands.add("take wreath");
                commands.add("east");
                commands.add("take fixed point");
                commands.add("west");
                commands.add("north");
                //commands.add("take infinite loop");
                commands.add("north");
                commands.add("take spool of cat6");
                commands.add("south");
                commands.add("south");
                commands.add("south");
                commands.add("south");
                //commands.add("take giant electromagnet");
                commands.add("south");
                commands.add("take candy cane");
                commands.add("north");
                commands.add("east");
                //commands.add("take escape pod");
                commands.add("east");
                commands.add("south");
                //commands.add("take photons");
                commands.add("north");
                commands.add("east");
                commands.add("take space law space brochure");
                commands.add("south");
                commands.add("take fuel cell");
                commands.add("south");
                commands.add("inv");

                commands.add("drop space law space brochure");
                commands.add("drop fixed point");
                commands.add("drop candy cane");
                commands.add("drop sand");
                commands.add("drop ornament");
                commands.add("drop fuel cell");
                commands.add("drop spool of cat6");
                commands.add("drop wreath");

                Set<String> itemSet = new HashSet<>(Arrays.asList("space law space brochure", "fixed point", "candy cane", "sand",
                        "ornament", "fuel cell", "spool of cat6", "wreath"));
                Set<Set<String>> combinations = Sets.powerSet(itemSet);
                for (Set<String> combination : combinations) {
                    for (String item : combination) {
                        commands.add("take " + item);
                    }
                    if (!combination.isEmpty()) {
                        commands.add("west");
                    }
                    for (String item : combination) {
                        commands.add("drop " + item);
                    }
                }

                commands.add("inv");
                commands.add("west");
                while (!commands.isEmpty()) {
                    String input = commands.poll();
                    System.out.println(input);
                    for (char c : input.toCharArray()) {
                        BigInteger bi = new BigInteger(String.valueOf((int) c));
                        outputQueue.add(bi);
                    }
                    outputQueue.add(new BigInteger("10"));
                }
            };
            executorService.submit(simpleInput);

            Thread.sleep(100000);
            return "hej";
        }
    }

    final ExecutorService executorService;
    private final List<String> opcodes;

    public Day25Cryostasis(List<String> inputLines) {
        executorService = Executors.newCachedThreadPool();
        opcodes = Stream.of(inputLines.get(0).split(","))
                .collect(Collectors.toList());
    }

    String getPassword() throws ExecutionException, InterruptedException {
        IntcodeComputer ic = new IntcodeComputer(opcodes);
        AsciiComputer ac = new AsciiComputer(executorService);
        ac.setInputQueue(ic.getOutputQueue());
        ac.setOutputQueue(ic.getInputQueue());

        executorService.submit(ic);
        Future<String> futureSum = executorService.submit(ac);

        return futureSum.get();
    }

}

// == Hull Breach ==
//You got in through a hole in the floor here. To keep your ship from also freezing, the hole has been sealed.
//
//Doors here lead:
//- east => Gift Wrapping Center
//- south => Crew Quarters
//- west => Sick Bay

//== Gift Wrapping Center ==
//How else do you wrap presents on the go?
//
//Doors here lead:
//- east => Kitchen
//- west => Hull Breach
//
//Items here:
//- sand

//== Sick Bay ==
//Supports both Red-Nosed Reindeer medicine and regular reindeer medicine.
//
//Doors here lead:
//- north => Hallway
//- east => Hull Breach
//- south => Arcade

//== Crew Quarters ==
//The beds are all too small for you.
//
//Doors here lead:
//- north => Hull Breach
//- east => Observatory
//
//Items here:
//- ornament

//== Observatory ==
//There are a few telescopes; they're all bolted down, though.
//
//Doors here lead:
//- west => Crew Quarters

//== Kitchen ==
//Everything's freeze-dried.
//
//Doors here lead:
//- west => Gift Wrapping Center
//
//Items here:
//- molten lava

//== Hallway ==
//This area has been optimized for something; you're just not quite sure what.
//
//Doors here lead:
//- north => Corridor
//- east => Engineering
//- south => Sick Bay
//
//Items here:
//- wreath

//== Arcade ==
//None of the cabinets seem to have power.
//
//Doors here lead:
//- north => Sick Bay
//- east => Warp Drive Maintenance
//- south => Holodeck
//
//Items here:
//- giant electromagnet

//== Warp Drive Maintenance ==
//It appears to be working normally.
//
//Doors here lead:
//- east => Navigation
//- south => Stables
//- west => Arcade
//
//Items here:
//- escape pod

//== Navigation ==
//Status: Stranded. Please supply measurements from fifty stars to recalibrate.
//
//Doors here lead:
//- east => Storage
//- south => Hot Chocolate Fountain
//- west => Warp Drive Maintenance

//== Storage ==
//The boxes just contain more boxes.  Recursively.
//
//Doors here lead:
//- south => Science Lab
//- west => Navigation
//
//Items here:
//- space law space brochure

//== Science Lab ==
//You see evidence here of prototype polymer design work.
//
//Doors here lead:
//- north => Storage
//- south => Security Checkpoint
//
//Items here:
//- fuel cell

//== Security Checkpoint ==
//In the next room, a pressure-sensitive floor will verify your identity.
//
//Doors here lead:
//- north => Science Lab
//- west => Pressure-Sensitive Floor

//== Pressure-Sensitive Floor ==
//Analyzing...
//
//Doors here lead:
//- east => Security Checkpoint
//
//A loud, robotic voice says "Alert! Droids on this ship are heavier than the detected value!" and you are ejected back to the checkpoint.

//== Engineering ==
//You see a whiteboard with plans for Springdroid v2.
//
//Doors here lead:
//- west => Hallway
//
//Items here:
//- fixed point

//== Corridor ==
//The metal walls and the metal floor are slightly different colors. Or are they?
//
//Doors here lead:
//- north => Passages
//- south => Hallway
//
//Items here:
//- infinite loop

//== Passages ==
//They're a little twisty and starting to look all alike.
//
//Doors here lead:
//- south => Corridor
//
//Items here:
//- spool of cat6

//== Holodeck ==
//Someone seems to have left it on the Giant Grid setting.
//
//Doors here lead:
//- north => Arcade
//
//Items here:
//- candy cane

//== Stables ==
//Reindeer-sized. They're all empty.
//
//Doors here lead:
//- north => Warp Drive Maintenance

//== Hot Chocolate Fountain ==
//Somehow, it's still working.
//
//Doors here lead:
//- north => Navigation
//
//Items here:
//- photons