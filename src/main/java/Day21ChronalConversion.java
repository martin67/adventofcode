import java.io.IOException;

public class Day21ChronalConversion {
    private Processor processor;

    public Day21ChronalConversion(String fileName) throws IOException {
        processor = new Processor(6);
        processor.readInstructions(fileName);
    }

    int lowestInteger() {
        int[] newRegister = {1, 0, 0, 0, 0, 0};
        processor.setRegister(newRegister);
        //implementPseudoCode();
        //processor.printPseudoCode();

        return processor.runCode(10000);
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
                    r5 = r1 + 1;
                    r5 = r5 * 256;
                }

                r3 = r1;

            } while (true);

        } while (r4 != r0);
    }
}
