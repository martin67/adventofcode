package aoc2019;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day8SpaceImageFormat {

    private final static int LAYER_SIZE = 25 * 6;

    static class Layer {
        Map<Integer, Integer> digitFrequency;

        Layer(String line) {
            digitFrequency = new HashMap<>();
            for (char c : line.toCharArray()) {
                int digit = Character.getNumericValue(c);
                if (digitFrequency.containsKey(digit)) {
                    digitFrequency.put(digit, digitFrequency.get(digit) + 1);
                } else {
                    digitFrequency.put(digit, 1);
                }
            }
        }
    }

    private List<Layer> layers;

    public Day8SpaceImageFormat(List<String> inputLines) {
        layers = new ArrayList<>();

        for (String line : inputLines) {
            int numberOfLayers = line.length() / LAYER_SIZE;
            log.info("Adding {} layers", numberOfLayers);
            for (int i = 0; i < numberOfLayers; i++) {
                layers.add(new Layer(line.substring(i * LAYER_SIZE, (i + 1) * LAYER_SIZE)));
            }
        }
    }

    int digitChecksum() {
        Layer fewestZeros = layers.stream()
                .min(Comparator.comparing(layer -> layer.digitFrequency.get(0))).get();

        return fewestZeros.digitFrequency.get(1) * fewestZeros.digitFrequency.get(2);
    }

}
