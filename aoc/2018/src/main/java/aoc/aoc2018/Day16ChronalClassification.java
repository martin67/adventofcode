package aoc.aoc2018;

import aoc.aoc2018.Processor.OpCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Day16ChronalClassification {

    private final Processor processor;
    Map<Integer, Integer> mappingTable = new HashMap<>();
    private List<Sample> samples;
    private List<UnknownInstruction> unknownInstructions;

    public Day16ChronalClassification(String input) {
        processor = new Processor(4);
        readData(input);
    }


    private void readData(String input) {
        List<String> inputStrings = Arrays.stream(input.split("[\\r?\\n]+"))
                .collect(Collectors.toList());

        Iterator<String> inputString = inputStrings.listIterator();
        samples = new ArrayList<>();
        unknownInstructions = new ArrayList<>();

        while (inputString.hasNext()) {
            String firstLine = inputString.next();
            if (StringUtils.startsWith(firstLine, "Before:")) {
                String before = StringUtils.substringBetween(firstLine, "Before: [", "]");
                String middle = inputString.next();
                String after = StringUtils.substringBetween(inputString.next(), "After:  [", "]");

                Sample sample = new Sample();
                sample.setRegisterBefore(Arrays.stream(before.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray());
                sample.setRegisterAfter(Arrays.stream(after.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray());
                int[] instruction = Arrays.stream(middle.split(" ")).mapToInt(Integer::parseInt).toArray();
                sample.setOpCode(instruction[0]);
                sample.setA(instruction[1]);
                sample.setB(instruction[2]);
                sample.setC(instruction[3]);
                samples.add(sample);
            } else {
                int[] row = Arrays.stream(firstLine.split(" ")).mapToInt(Integer::parseInt).toArray();
                unknownInstructions.add(new UnknownInstruction(row[0], row[1], row[2], row[3]));
            }
        }
    }

    int threeOrMoreOpCodes() {
        int threeorMoreOpCodes = 0;

        for (Sample sample : samples) {
            int numberOfCorrectOpCodes = 0;
            for (OpCode opCode : processor.getOpCodes()) {
                processor.setRegister(sample.getRegisterBefore().clone());
                opCode.operator(sample.getA(), sample.getB(), sample.getC());
                if (Arrays.equals(processor.getRegister(), sample.getRegisterAfter())) {
                    System.out.println("Match for sample " + sample + " and opcode " + opCode.getName());
                    numberOfCorrectOpCodes++;
                }
            }
            if (numberOfCorrectOpCodes >= 3) {
                threeorMoreOpCodes++;
            }
        }
        return threeorMoreOpCodes;
    }

    int remainingRegister() {

        List<OpCode> opCodesToResolve = new ArrayList<>(processor.getOpCodes());
        List<Sample> samplesToResolve = new ArrayList<>(samples);

        while (!opCodesToResolve.isEmpty()) {
            Map<Integer, List<String>> matches = new HashMap<>();
            for (OpCode opCode : opCodesToResolve) {
                // find all samples with opcode = i

                for (int i = 0; i < 16; i++) {
                    boolean foundSomething = false;
                    boolean foundMismatch = false;
                    int finalI = i;
                    for (Sample sample : samplesToResolve.stream().filter(s -> s.getOpCode() == finalI).toList()) {
                        foundSomething = true;
                        processor.setRegister(sample.getRegisterBefore().clone());
                        opCode.operator(sample.getA(), sample.getB(), sample.getC());
                        if (!Arrays.equals(processor.getRegister(), sample.getRegisterAfter())) {
                            foundMismatch = true;
                        }
                        //System.out.println("Match for sample " + sample + " and opcode " + opCode.getName());
                    }
                    if (foundSomething && !foundMismatch) {
                        System.out.println("Match for opcode " + opCode + " and sample " + i);
                        // add sample index to matches map
                        if (!matches.containsKey(i)) {
                            matches.put(i, new ArrayList<>());
                        }
                        matches.get(i).add(opCode.getName());
                    }
                }


            }
            // check which opcode and sample that matches
            System.out.println("Matches: " + matches);
            for (int sampleId : matches.keySet()) {
                System.out.println(sampleId + ": " + matches.get(sampleId));
            }
            // Find key where length of the valuelist is 1
            for (int key : matches.keySet()) {
                if (matches.get(key).size() == 1) {
                    String opCodeName = matches.get(key).get(0);
                    System.out.println("Found match for " + key + " for opcode " + opCodeName);
                    System.out.println("saving and removing from");
                    OpCode opCode = opCodesToResolve.stream().filter(o -> o.getName().equals(opCodeName)).findFirst().orElse(null);
                    assert opCode != null;
                    opCode.setSampleId(key);
                    opCodesToResolve.remove(opCode);
                    samplesToResolve.removeIf(s -> s.getOpCode() == key);
                }
            }
        }


        // evaluate instructions
        int[] register = new int[4];
        for (UnknownInstruction unknownInstruction : unknownInstructions) {
            OpCode opCode = processor.getOpCodes().stream().filter(o -> o.getSampleId() == unknownInstruction.sampleIndex).findFirst().orElse(null);
            assert opCode != null;
            processor.setRegister(register);
            opCode.operator(unknownInstruction.getA(), unknownInstruction.getB(), unknownInstruction.getC());
        }
        System.out.println("Final register: " + Arrays.toString(register));
        return register[0];
    }

    @Data
    static class Sample {
        int[] registerBefore;
        int opCode;
        int a;
        int b;
        int c;
        int[] registerAfter;
    }

    @Data
    @AllArgsConstructor
    static class UnknownInstruction {
        int sampleIndex;
        int a;
        int b;
        int c;
    }


}


