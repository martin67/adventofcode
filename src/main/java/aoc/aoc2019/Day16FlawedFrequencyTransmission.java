package aoc.aoc2019;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Day16FlawedFrequencyTransmission {

    List<Integer> fft;

    public Day16FlawedFrequencyTransmission(String input) {
        fft = input.chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());
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
        log.info("Starting FFT, {} phases, fft size: {}", phases, fft.size());
        for (int phase = 0; phase < phases; phase++) {

            List<Integer> nextValues = new ArrayList<>();
            for (int row = 0; row < fft.size(); row++) {
                int value = 0;
                for (int position = 0; position < fft.size(); position++) {
                    value += pattern(fft.get(position), position, row);
                }
                String valueString = String.valueOf(value);
                nextValues.add(row, Integer.valueOf(valueString.substring(valueString.length() - 1)));
            }
            fft = nextValues;
            log.debug("After {} phase, {}", phase, fft);
            log.info("After {} phase", phase);
        }
        return fft.stream().map(String::valueOf).collect(Collectors.joining()).substring(0, 8);
    }

    String finalOutput(int phases) {
        List<Integer> initialFft = new ArrayList<>(fft);

        for (int i = 0; i < 10000; i++) {
            fft.addAll(initialFft);
        }
        String out = firstEightDigits(1000);
        return "00000000";
    }

    String apacheFft(int phases) {
        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);

        double[] fftdata = fft.stream().mapToDouble(x -> x).toArray();

        Complex[] hej = transformer.transform(fftdata, TransformType.FORWARD);
        Complex[] hej2 = transformer.transform(fftdata, TransformType.INVERSE);

        return "00000000";
    }

}