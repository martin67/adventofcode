package aoc.aoc2019;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day16FlawedFrequencyTransmission {

    int[] fft;

    public Day16FlawedFrequencyTransmission(String input) {
        log.info("Creating fft");
        fft = input.chars().map(x -> x - '0').toArray();
    }

    int pattern(int value, int position, int row) {
        position++;

        int group = position / (row + 1);
        switch (group % 4) {
            case 0:
            case 2:
                return 0;
            case 1:
                return value;
            case 3:
                return -value;
            default:
                log.error("Oops");
                return 0;
        }
    }

    String firstEightDigits(int phases) {
        log.info("Starting FFT, {} phases, fft size: {}", phases, fft.length);
        for (int phase = 0; phase < phases; phase++) {
            log.debug("Running FFT, phase {}", phase);
            int[] nextValues = new int[fft.length];

            for (int row = 0; row < fft.length; row++) {
                int value = 0;
                for (int position = 0; position < fft.length; position++) {
                    value += pattern(fft[position], position, row);
                }
                nextValues[row] = Math.abs(value % 10);
            }
            fft = nextValues;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(fft[i]);
        }
        return sb.toString();
    }

    String finalOutput(int phases, int multiplier) {
        int totalLength = fft.length * multiplier;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            sb.append(fft[i]);
        }
        int offset = Integer.parseInt(sb.toString());
        log.info("fft length {}, multiplier {}, total length {}, offset {}", fft.length, multiplier, totalLength, offset);

        // As offset > totalLength / 2, there will only be 1:s in the multiplication matrix
        // The original fft (fft * multiplier) can be reduced
        // Create a new fft that starts from offset and goes to the real end
        // first start from an multiple of fft
        int index = offset / fft.length;
        int remainder = offset % fft.length;
        log.info("Creating new fft, starting from fft # {}, pos {}", index, remainder);
        int[] newFft = new int[(multiplier - index) * fft.length - remainder];

        System.arraycopy(fft, remainder, newFft, 0, fft.length - remainder);
        for (int i = 0; i < multiplier - index - 1; i++) {
            System.arraycopy(fft, 0, newFft, (fft.length - remainder) + fft.length * i, fft.length);
        }

        log.info("new FFT length: {}", newFft.length);

//        int[] nextValues = new int[newFft.length];

        for (int phase = 0; phase < phases; phase++) {
//            log.info("Phase {}", phase);
//            for (int row = 0; row < newFft.length; row++) {
//                int value = 0;
//                for (int position = row; position < newFft.length; position++) {
//                    value += newFft[position];
//                }
//                nextValues[row] = value % 10;
//            }
//            newFft = nextValues;

            for (int i = newFft.length - 2; i >= 0; i--) {
                newFft[i] = (newFft[i] + newFft[i + 1]) % 10;
            }
        }

        sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(newFft[i]);
        }
        return sb.toString();
    }
}
