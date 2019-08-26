import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day21ChronalConversion {
    private Processor processor;

    public Day21ChronalConversion(String fileName) throws IOException {
        processor = new Processor(6);
        processor.readInstructions(fileName);
        //processor.printPseudoCode();
    }

    int fewestInstructions() {
        int[] newRegister = {0, 0, 0, 0, 0, 0};
        processor.setRegister(newRegister);

        return runCodeUntilIp(28);
    }

    // The solution is to see what the value of r4 is when we get to ip=28
    //   28: if r4 == r0 then r1 = 1 else r1 = 0
    private int runCodeUntilIp(int ip) {
        while (processor.getInstructionPointer() != ip) {
            processor.saveInstructionPointer();
            Instruction instruction = processor.getInstructions().get(processor.getInstructionPointer());
            instruction.getOpCode().operator(instruction.getA(), instruction.getB(), instruction.getC());
            processor.loadInstructionPointer();
            processor.incrementInstructionPointer();
        }
        return processor.getRegister()[4];
    }

    int mostInstructions() {
        int[] newRegister = {0, 0, 0, 0, 0, 0};
        processor.setRegister(newRegister);

        int iterations = runCodeUntilCheck(28, Integer.MAX_VALUE);
        // Look for repeating pattern
        System.out.println(processor.registerToString());
        return iterations;
    }

    private int runCodeUntilCheck(int ip, int maxIterations) {
        boolean programEnd = false;
        int iterations = 0;
        List<Integer> r4list = new ArrayList<>();

        while (!programEnd && iterations < maxIterations) {
            if (processor.getInstructionPointer() == ip) {
//                System.out.printf("(%4d) ip=%-6d %35s\n", iterations, processor.getInstructionPointer(), processor.registerToString());
                if (r4list.contains(processor.getRegister()[4])) {
                    System.out.printf("(%4d) ip=%-6d %35s\n", iterations, processor.getInstructionPointer(), processor.registerToString());
                    System.out.printf("Found match for r4 = %d at ip ? %d\n", processor.getRegister()[4], processor.getInstructionPointer());
                } else {
                    r4list.add(processor.getRegister()[4]);
                }
            }
            processor.saveInstructionPointer();
//            System.out.printf("(%4d) ip=%-6d %35s", iterations, getInstructionPointer(), registerToString());
            Instruction instruction = processor.getInstructions().get(processor.getInstructionPointer());
            // update the instruction pointer register to the current value of the instruction pointer

            instruction.getOpCode().operator(instruction.getA(), instruction.getB(), instruction.getC());
//            System.out.printf(" => %-35s  %-40s %s", registerToString(), instruction.getOpCode().pseudoCode(instruction.getA(),
//                    instruction.getB(), instruction.getC()), instruction);
//            System.out.println();
            processor.loadInstructionPointer();
            processor.incrementInstructionPointer();
            if (processor.getInstructionPointer() >= processor.getInstructions().size()) {
                programEnd = true;
            }
            iterations++;
        }
        Collections.sort(r4list);
        for (int r4 : r4list) {
            System.out.println(r4);
        }
        return iterations;
    }

//            0: r4 = 123
//            1: r4 = r4 AND 456
//            2: if r4 == 72 then r4 = 1 else r4 = 0
//            3: r2 = r2 + r4
//            4: r2 = 0
//            5: r4 = 0
//         -> 6: r3 = r4 OR 65536
//            7: r4 = 10283511
//         -> 8: r1 = r3 AND 255
//            9: r4 = r1 + r4
//            10: r4 = r4 AND 16777215
//            11: r4 = r4 * 65899
//            12: r4 = r4 AND 16777215
//            13: if 256 > r3 then r1 = 1 else r1 = 0
//            14: r2 = r1 + r2
//            15: r2 = r2 + 1
//         <- 16: r2 = 27
//            17: r1 = 0
//         -> 18: r5 = r1 + 1
//            19: r5 = r5 * 256
//            20: if r5 > r3 then r5 = 1 else r5 = 0
//            21: r2 = r2 + r5
//            22: r2 = r2 + 1
//         <- 23: r2 = 25
//            24: r1 = r1 + 1
//         <- 25: r2 = 17
//         -> 26: r3 = r1
//         <- 27: r2 = 7
//         -> 28: if r4 == r0 then r1 = 1 else r1 = 0
//            29: r2 = r1 + r2
//         <- 30: r2 = 5

    // when IP = 28, r4 = 13443200

    void implementPseudoCode() {
        int r0 = 0;
        int r1 = 0;
        int r2 = 0;
        int r3 = 0;
        int r4 = 0;
        int r5 = 0;

        r4 = 123;
        do {
            r4 = r4 & 456;
        } while (r4 != 72);
        r4 = 0;

        do {
            r3 = r4 | 65536;   // r3 | 0x10000
            r4 = 10283511;

            do {
                // r4 = F(r3)
                r1 = r3 & 255;
                r4 = r1 + r4;
                r4 = r4 & 16777215;
                r4 = r4 * 65899;
                r4 = r4 & 16777215;

                if (r3 < 256) {
                    break;
                }

                for (r1 = 0; r3 > r5; r1++) {
//                    r5 = r1 + 1;
//                    r5 = r5 * 256;
                    r5 = 256 * r1 + 256;
                }

                r3 = r1;

            } while (true);

        } while (r4 != r0);
    }
}
