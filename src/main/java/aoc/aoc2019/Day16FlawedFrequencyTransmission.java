package aoc.aoc2019;

import lombok.extern.slf4j.Slf4j;

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
            System.out.println(phase);
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

    String finalOutput(int phases, int multiplier) {
        List<Integer> initialFft = new ArrayList<>(fft);
        log.info("Starting FFT, {} phases, fft size: {}", phases, fft.size());

        // Extend the fft, not with multiplier times, but the minimum needed (as the pattern repeats)
        int maxIterationsNeeded = 4 * fft.size();
        List<Integer> extendedFft = new ArrayList<>();
        for (int i = 0; i < maxIterationsNeeded; i++) {
            extendedFft.addAll(fft);
        }
        log.info("Extended FFT, fft size: {}", extendedFft.size());

        // Loop through all phases
        for (int phase = 0; phase < phases; phase++) {
            log.info("Running FFT, phase {}", phase);
            List<Integer> nextValues = new ArrayList<>();

            // For each phase, go through all rows
            for (int row = 0; row < extendedFft.size(); row++) {

                // For each row, go through all positions in the list
                int value = 0;
                for (int fftIndex = 0; fftIndex < extendedFft.size(); fftIndex++) {
                    value += pattern(extendedFft.get(fftIndex), fftIndex, row);
                }
                // Adjust for the multiplier
                int multipliedValue = value * (multiplier / maxIterationsNeeded);
               // log.info("row {}, value {}, multipled value {}", row, value, multipliedValue);
                String valueString = String.valueOf(multipliedValue);
                nextValues.add(row, Integer.valueOf(valueString.substring(valueString.length() - 1)));
            }
            extendedFft = nextValues;
            //log.info("After {} phase, {}", phase, extendedFft);
            //log.info("After {} phase", phase);
        }
        String offset = extendedFft.stream().map(String::valueOf).collect(Collectors.joining()).substring(0, 7);
        return offset;
    }

}

// Idé för del 2:
//
// Pattern (9 x 11): 123456789 123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789
// 1                 +0-0+0-0+ 0-0+0-0+0-0+0-0+0-0+0-0+0-0+0-0+0-0+  upprepar sig efter 4 ggr
// 2                 0++00--00 ++00--00++00--00++00--00++00--00++00--00++00--00++00--00++00--0  upprepar sig efter 8ggr
// 3                 00+++000- --000+++000---000+++000---
// 4                 000++++00 00----0000++++0000----
// 5                 0000+++++ 00000-----00000+++++00000-----
// 6                 00000++++ ++000000------000000++++++000000------
// 7                 000000+++ ++++0000000-------
// 8                 0000000++ ++++++00000000--------
// 9                 00000000+ ++++++++000000000---------

// Mönstret (och därigenom värdena) upprepas var 4 * sifferposition
// så värdet på position x och x + (sifferposition * längd) likadant

// Nytt test med matrismultiplikation, kan det hjälpa?

// En lista med 5 poster:

// 1 2 3 4 5    1  0 -1  0  1
// 1 2 3 4 5    0  1  1  0  0
// 1 2 3 4 5    0  0  1  1  1
// 1 2 3 4 5    0  0  0  1  1
// 1 2 3 4 5    0  0  0  0  1

// Det fungerar om man multiplicerar följande (byt plats på rader och kolumner = transpose)

// 1 2 3 4 5   x   1 0 0 0 0   =   3 5 12 9 5
//                 0 1 0 0 0
//                -1 1 1 0 0
//                 0 0 1 1 0
//                 1 0 1 1 1
