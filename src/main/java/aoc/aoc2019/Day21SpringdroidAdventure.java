package aoc.aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day21SpringdroidAdventure {

    @Data
    static class AsciiComputerInput implements Callable<Integer> {
        private BlockingQueue<BigInteger> outputQueue;
        boolean partTwo;

        public AsciiComputerInput(boolean partTwo) {
            this.partTwo = partTwo;
        }

        @Override
        public Integer call() {
            Queue<String> commands = new LinkedList<>();
            if (!partTwo) {
                // (A or B or C == hole) and D == ground
                commands.add("NOT A T");
                commands.add("NOT B J");
                commands.add("OR T J");
                commands.add("NOT C T");
                commands.add("OR T J");
                commands.add("AND D J");
                commands.add("WALK");
            } else {
                commands.add("NOT E T");
                commands.add("NOT I J");
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

    final ExecutorService executorService;
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

// ....@............     .................
// #####.###########    #####@.#.########

// .....@...........    .................
// #####.###########    #####@###########

// 1: jump if A=hole && C=ground (A=false, C=true) => NOT A J, AND C J
// 2: jump if B=hole &&
// 3. jump if C=true

// if C=true jump

// NOT A J => #####..#.########     jump if A=hole
// NOT B J => #####...#########     jump if B=hole
// NOT C J => #####...#########     jump if C=hole
// NOT D J => #####.###########     jump if D=hole
// NOT A J, AND C J => #####...#########
// NOT A J, AND D J => #####..#.########
// NOT A J, A=hole && C AND D == ground

// NOT A J, AND D J or NOT B J, and D J
// NOT D J - D skall vara mark, dvs J blir då false
// A or B = hole && D = ground
// svar; A or B or C = hole & D = ground

// A or B or C = hole && D = ground && E = ground

//      *
//     * *
//    *   *
// #####.#.#...#.###
//      ABCDEFGHI
//     ABCDEFGHI
//    ABCDEFGHI
//   ABCDEFGHI
//  ABCDEFGHI

// E && I == ground