package aoc.aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day21SpringdroidAdventure {

    private final ExecutorService executorService;
    private final List<String> opcodes;

    public Day21SpringdroidAdventure(List<String> inputLines) {
        executorService = Executors.newCachedThreadPool();
        opcodes = Stream.of(inputLines.get(0).split(","))
                .collect(Collectors.toList());
    }

    String hullDamage(boolean partTwo) throws ExecutionException, InterruptedException {
        IntcodeComputer ic = new IntcodeComputer(opcodes);
        AsciiComputerInput input = new AsciiComputerInput(partTwo);
        AsciiComputerOutput output = new AsciiComputerOutput();
        input.setOutputQueue(ic.getInputQueue());
        output.setInputQueue(ic.getOutputQueue());

        executorService.submit(ic);
        executorService.submit(input);
        Future<String> futureSum = executorService.submit(output);

        return futureSum.get();
    }

    @Data
    static class AsciiComputerInput implements Callable<Integer> {
        boolean partTwo;
        private BlockingQueue<BigInteger> outputQueue;

        public AsciiComputerInput(boolean partTwo) {
            this.partTwo = partTwo;
        }

        @Override
        public Integer call() {
            Queue<String> commands = new LinkedList<>();
            if (!partTwo) {
                // jump if (A or B or C == hole) and D == ground
                commands.add("NOT A T");
                commands.add("NOT B J");
                commands.add("OR T J");
                commands.add("NOT C T");
                commands.add("OR T J");
                commands.add("AND D J");
                commands.add("WALK");
            } else {
                // (!A || !B || !C) && D && (E || H))
                commands.add("NOT A T");
                commands.add("NOT B J");
                commands.add("OR T J");
                commands.add("NOT C T");
                commands.add("OR T J");
                commands.add("AND D J");
                commands.add("NOT E T");
                commands.add("NOT T T");
                commands.add("OR H T");
                commands.add("AND T J");
                commands.add("RUN");
            }
            while (!commands.isEmpty()) {
                String input = commands.poll();
                System.out.println(input);
                for (char c : input.toCharArray()) {
                    BigInteger bi = new BigInteger(String.valueOf((int) c));
                    outputQueue.add(bi);
                }
                outputQueue.add(new BigInteger("10"));
            }
            return 0;
        }
    }

    @Data
    static class AsciiComputerOutput implements Callable<String> {
        private BlockingQueue<BigInteger> inputQueue;

        @Override
        public String call() {
            while (true) {
                BigInteger output = null;
                try {
                    output = inputQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //log.info("BigInteger: {}", output);
                assert output != null;
                if (output.compareTo(new BigInteger("10000")) > 0) {
                    return output.toString();
                } else {
                    System.out.print((char) output.intValue());
                }
            }
        }
    }
}
