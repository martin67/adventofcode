package aoc.aoc2023;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
class Day15LensLibrary {
    String input;

    Day15LensLibrary(List<String> inputLines) {
        for (String line : inputLines) {
            input = line;
        }
    }

    int problem1() {
        int sum = 0;
        for (String in : input.split(",")) {
            sum += hash(in);
        }
        return sum;
    }

    int problem2() {
        Pattern pattern = Pattern.compile("(\\w+)(-|=)(\\d+)?,?");
        var matcher = pattern.matcher(input);
        List<LinkedList<Lens>> boxes = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            boxes.add(new LinkedList<>());
        }

        while (matcher.find()) {
            String tag = matcher.group(1);
            String operation = matcher.group(2);
            int focalLength = operation.equals("=") ? Integer.parseInt(matcher.group(3)) : -1;
            int boxNumber = hash(tag);
            Lens newLens = new Lens(tag, focalLength);
            var box = boxes.get(boxNumber);

            if (operation.equals("-")) {
                box.removeIf(lens -> lens.tag.equals(newLens.tag));
            } else {
                Optional<Lens> existingLens = box.stream().filter(lens -> lens.tag.equals(newLens.tag)).findFirst();
                if (existingLens.isPresent()) {
                    existingLens.get().focalLength = focalLength;
                } else {
                    box.add(newLens);
                }

            }
        }

        int power = 0;
        for (int i = 0; i < boxes.size(); i++) {
            var box = boxes.get(i);
            for (int j = 0; j < box.size(); j++) {
                var lens = box.get(j);
                power += (i + 1) * (j + 1) * lens.focalLength;
            }
        }
        return power;
    }

    int hash(String in) {
        int currentValue = 0;
        for (char c : in.toCharArray()) {
            currentValue += c;
            currentValue *= 17;
            currentValue = currentValue % 256;
        }
        return currentValue;
    }

    static class Lens {
        String tag;
        int focalLength;

        public Lens(String tag, int focalLength) {
            this.tag = tag;
            this.focalLength = focalLength;
        }
    }
}
