package aoc.aoc2020;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14DockingData {
    final Map<Long, Long> memory = new HashMap<>();
    String mask;
    final List<String> inputLines;

    public Day14DockingData(List<String> inputLines) {
        this.inputLines = inputLines;
        mask = "0".repeat(36);
    }

    long problem1() {
        // mask = 11110X1XXX11001X01X00011001X00X00000
        // mem[28496] = 122879146
        Pattern maskPattern = Pattern.compile("^mask = (\\w+)$");
        Pattern memPattern = Pattern.compile("^mem\\[(\\d+)] = (\\d+)$");
        Matcher matcher;

        for (String line : inputLines) {
            matcher = maskPattern.matcher(line);
            if (matcher.find()) {
                mask = matcher.group(1);
            }

            matcher = memPattern.matcher(line);
            if (matcher.find()) {
                long pos = Long.parseLong(matcher.group(1));
                long value = Long.parseLong(matcher.group(2));

                long nextvalue = 0;
                long old;
                for (int i = 0; i < 36; i++) {
                    long masken = (long) Math.pow(2, i);
                    old = value & masken;
                    switch (mask.charAt(36 - i - 1)) {
                        case 'X':
                            nextvalue += old;
                            break;
                        case '0':
                            break;
                        case '1':
                            nextvalue += Math.pow(2, i);
                            break;
                    }
                }
                memory.put(pos, nextvalue);
            }
        }
        return memory.values().stream().mapToLong(Long::longValue).sum();
    }

    long problem2() {
        Pattern maskPattern = Pattern.compile("^mask = (\\w+)$");
        Pattern memPattern = Pattern.compile("^mem\\[(\\d+)] = (\\d+)$");
        Matcher matcher;

        for (String line : inputLines) {
            matcher = maskPattern.matcher(line);
            if (matcher.find()) {
                mask = matcher.group(1);
            }

            matcher = memPattern.matcher(line);
            if (matcher.find()) {
                long pos = Long.parseLong(matcher.group(1));
                long value = Long.parseLong(matcher.group(2));

                char[] addressMask = new char[36];
                for (int i = 0; i < 36; i++) {
                    switch (mask.charAt(i)) {
                        case '0':
                            if ((pos & (long) Math.pow(2, 35 - i)) > 0) {
                                addressMask[i] = '1';
                            } else {
                                addressMask[i] = '0';
                            }
                            break;
                        case 'X':
                            addressMask[i] = 'X';
                            break;
                        case '1':
                            addressMask[i] = '1';
                            break;
                    }
                }

                // generate all possible addresses from addressmask
                Set<String> addresses = generateAddresses(String.valueOf(addressMask), 0);
                for (String address : addresses) {
                    long addr = Long.valueOf(address, 2);
                    memory.put(addr, value);
                }
            }
        }
        return memory.values().stream().mapToLong(Long::longValue).sum();
    }

    Set<String> generateAddresses(String mask, int startIndex) {
        Set<String> adresses = new HashSet<>();
        int index = startIndex;
        while (index < 36 && mask.charAt(index) != 'X') {
            index++;
        }

        if (index == 36) {
            return Set.of(mask);
        }
        adresses.addAll(generateAddresses(mask.substring(0, index) + "0" + mask.substring(index + 1), index));
        adresses.addAll(generateAddresses(mask.substring(0, index) + "1" + mask.substring(index + 1), index));

        return adresses;
    }
}
