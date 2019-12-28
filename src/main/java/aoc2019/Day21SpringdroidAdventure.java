package aoc2019;

import com.google.common.collect.Sets;
import lombok.Data;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day21SpringdroidAdventure {

    @Data
    static class AsciiComputer implements Callable<Integer> {
        ExecutorService executorService;
        private BlockingQueue<BigInteger> inputQueue;
        private BlockingQueue<BigInteger> outputQueue;

        public AsciiComputer(ExecutorService executorService) {
            this.executorService = executorService;
        }

        @Override
        public Integer call() throws Exception {
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
                commands.add("NOT D T");
                commands.add("NOT T J");

//                commands.add("NOT B T");
//                commands.add("AND T J");
//                commands.add("NOT C T");
//                commands.add("AND T J");
//                commands.add("AND D J");
                commands.add("WALK");
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

            return 0;
        }
    }

    final ExecutorService executorService;
    private final List<String> opcodes;

    public Day21SpringdroidAdventure(List<String> inputLines) {
        executorService = Executors.newCachedThreadPool();
        opcodes = Stream.of(inputLines.get(0).split(","))
                .collect(Collectors.toList());
    }

    int hullDamage() throws ExecutionException, InterruptedException {
        IntcodeComputer ic = new IntcodeComputer(opcodes);
        AsciiComputer ac = new AsciiComputer(executorService);
        ac.setInputQueue(ic.getOutputQueue());
        ac.setOutputQueue(ic.getInputQueue());

        executorService.submit(ic);
        Future<Integer> futureSum = executorService.submit(ac);

        return futureSum.get();
    }
}

// Possible cases. can only jump 3?
// 1234ABCD            1   2   3   4
// ####.####   1000    no  ok  ok  ok
// ####.##.#   1001    no  ok  ok  no
// ####.#.##   1010    no  ok  no  ok
// ####.#..#   1011    no  ok  no  no
// ####..###   1100    no  no  ok  ok
// ####..#.#   1101    no  no  ok  no
// ####...##   1110    no  no  no  ok
// ####....#   1111    no  no  no  no

// ground = true, hole = false
// jumps 3 positions
// falls down in hole if empty below or next

// ....@............    .................
// #####.###########    #####@.#.########

// .....@...........    .................
// #####.###########    #####@###########

// 1: if A=true && B=true && C=true && D=false => J = false
// 2: jump if A=false && C=true
// 3. jump if C=true

// if C=true jump