import com.sun.org.glassfish.gmbal.ManagedAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Data
class OpCode {
    int[] register;
    private String name;

    OpCode(String name) {
        this.name = name;
    }

    void operator(int a, int b, int c) {
    }

    void printRegister() {
        for (int i = 0; i < 4; i++) {
            System.out.print(register[i] + " ");
        }
        System.out.println();
    }
}

@Data
class Sample {
    int[] registerBefore;
    int opCode;
    int a;
    int b;
    int c;
    int[] registerAfter;
}

public class Day16ChronalClassification {

    private List<OpCode> opCodes;
    private List<Sample> samples;

    public Day16ChronalClassification(String input) {
        setupOpCodes();
        readData(input);
    }

    private void setupOpCodes() {
        opCodes = new ArrayList<>();
        //int[] register = new int[4];

        opCodes.add(new OpCode("addr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] + register[b];
            }
        });

        opCodes.add(new OpCode("addi") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] + b;
            }
        });

        opCodes.add(new OpCode("mulr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] * register[b];
            }
        });

        opCodes.add(new OpCode("muli") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] * b;
            }
        });

        opCodes.add(new OpCode("banr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] & register[b];
            }
        });

        opCodes.add(new OpCode("bani") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] & b;
            }
        });

        opCodes.add(new OpCode("borr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] | register[b];
            }
        });

        opCodes.add(new OpCode("bori") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] | b;
            }
        });

        opCodes.add(new OpCode("setr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a];
            }
        });

        opCodes.add(new OpCode("seti") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = a;
            }
        });

        opCodes.add(new OpCode("gtir") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = a > register[b] ? 1 : 0;
            }
        });

        opCodes.add(new OpCode("gtri") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] > b ? 1 : 0;
            }
        });

        opCodes.add(new OpCode("gtrr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] > register[b] ? 1 : 0;
            }
        });

        opCodes.add(new OpCode("eqir") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = a == register[b] ? 1 : 0;
            }
        });

        opCodes.add(new OpCode("eqri") {
            void operator(int a, int b, int c) {
                register[c] = register[a] == b ? 1 : 0;
            }
        });

        opCodes.add(new OpCode("eqrr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] == register[b] ? 1 : 0;
            }
        });
    }

    private void readData(String input) {
        List<String> inputStrings = Arrays.stream(input.split("\\n+"))
                .collect(Collectors.toList());

        Iterator<String> inputString = inputStrings.listIterator();
        samples = new ArrayList<>();

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
            }
        }
    }

    int threeOrMoreOpCodes() {
        int threeorMoreOpCodes = 0;

        for (Sample sample : samples) {
            int numberOfCorrectOpCodes = 0;
            for (OpCode opCode : opCodes) {
                opCode.setRegister(sample.getRegisterBefore().clone());
                opCode.operator(sample.getA(), sample.getB(), sample.getC());
                if (Arrays.equals(opCode.getRegister(), sample.getRegisterAfter())) {
                    //System.out.println("Match for sample " + sample + " and opcode " + opCode.getName());
                    numberOfCorrectOpCodes++;
                }
            }
            if (numberOfCorrectOpCodes >= 3) {
                threeorMoreOpCodes++;
            }
        }
        return threeorMoreOpCodes;
    }
}
