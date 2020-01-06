package aoc.aoc2019;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day8SpaceImageFormat {

    private final static int LAYER_WIDTH = 25;
    private final static int LAYER_HEIGHT = 6;
    private final static int LAYER_SIZE = LAYER_WIDTH * LAYER_HEIGHT;

    static class Layer {
        final String pixels;

        Layer(String line) {
            this.pixels = line;
        }

        Map<Integer, Integer> getDigitFrequency() {
            Map<Integer, Integer> digitFrequency = new HashMap<>();
            for (char c : this.pixels.toCharArray()) {
                int digit = Character.getNumericValue(c);
                if (digitFrequency.containsKey(digit)) {
                    digitFrequency.put(digit, digitFrequency.get(digit) + 1);
                } else {
                    digitFrequency.put(digit, 1);
                }
            }
            return digitFrequency;
        }

        @Override
        public String toString() {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < LAYER_HEIGHT; i++) {
                output.append(this.pixels, i * LAYER_WIDTH, (i + 1) * LAYER_WIDTH).append("\n");
            }
            return output.toString().replaceAll("0", ".");
        }
    }

    private final List<Layer> layers;

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

    int digitChecksum() throws Exception {
        Layer fewestZeros = layers.stream()
                .min(Comparator.comparing(layer -> layer.getDigitFrequency().get(0)))
                .orElseThrow(() -> new Exception("No min found!"));
        printMessage();
        return fewestZeros.getDigitFrequency().get(1) * fewestZeros.getDigitFrequency().get(2);
    }

    private void printMessage() {
        StringBuilder finalString = new StringBuilder();

        for (int i = 0; i < LAYER_SIZE; i++) {
            char pixel = 'X';
            for (Layer layer : layers) {
                pixel = layer.pixels.charAt(i);
                if (pixel == '0' || pixel == '1') {
                    break;
                }
            }
            finalString.append(pixel);
        }
        Layer finalLayer = new Layer(finalString.toString());
        System.out.println(finalLayer);
    }
}