package aoc.aoc2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day7InternetProtocolVersion7 {

    private List<String> addresses;

    public Day7InternetProtocolVersion7(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        addresses = Files.readAllLines(Paths.get(fileName));
    }

    int supportTLS() {
        int numberOfIps = 0;

        for (String address : addresses) {
            char[] c = address.toCharArray();
            boolean supportTLS = false;
            boolean hypernetSequence = false;

            for (int i = 0; i < c.length - 3; i++) {
                if (c[i] == '[') {
                    hypernetSequence = true;
                } else if (c[i] == ']') {
                    hypernetSequence = false;
                } else if (c[i] != c[i + 1] && c[i] == c[i + 3] && c[i + 1] == c[i + 2]) {
                    if (hypernetSequence) {
                        supportTLS = false;
                        break;
                    } else {
                        supportTLS = true;
                        i += 3;
                    }
                }
            }
            if (supportTLS) {
                numberOfIps++;
            }
        }
        return numberOfIps;
    }

    int supportSSL() {
        int numberOfIps = 0;
        boolean hypernetSequence = false;

        for (String address : addresses) {
            char[] c = address.toCharArray();
            Set<String> abaSet = new HashSet<>();
            Set<String> babSet = new HashSet<>();

            for (int i = 0; i < c.length - 2; i++) {
                if (c[i] == '[') {
                    hypernetSequence = true;
                } else if (c[i] == ']') {
                    hypernetSequence = false;
                } else if (c[i] == c[i + 2] && c[i] != c[i + 1] && c[i + 1] != '[' && c[i + 1] != ']') {
                    String block = String.valueOf(c[i]) + c[i + 1] + c[i + 2];
                    if (hypernetSequence) {
                        babSet.add(block);
                    } else {
                        abaSet.add(block);
                    }
                }
            }
            for (String aba : abaSet) {
                String bab = String.valueOf(aba.charAt(1)) + aba.charAt(0) + aba.charAt(1);
                if (babSet.contains(bab)) {
                    numberOfIps++;
                    break;
                }
            }
        }
        return numberOfIps;
    }

}
