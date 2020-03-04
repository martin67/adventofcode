package aoc.aoc2019;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

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
            log.info("Running FFT, phase {}", phase);
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
        log.info("fft length {}, multiplier {}, total length {}", fft.length, multiplier, fft.length * multiplier);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            sb.append(fft[i]);
        }
        int offset = Integer.parseInt(sb.toString());
        log.info("offset {}", offset);

        int o2 = offset % fft.length;
        log.info("second offset {}", o2);

        log.info("fft at second offset: {}", Arrays.copyOfRange(fft, o2, o2 + 8));
        int[] fft2 = Arrays.copyOfRange(fft, o2, o2 + 8);

        // only +

        for (int i = 0; i < phases; i++) {
            // beräkna bara nedre högra hörnet; alla koefficienter är 1
            for (int row = offset; row < fft.length * multiplier; row++) {
                int value = 0;
                for (int position = 0; position < fft.length; position++) {
                    value += pattern(fft[position], position, row);
                }
                nextValues[row] = Math.abs(value % 10);
            }
            fft = nextValues;
        }

        return "";
    }

    String finalOutput2(int phases, int multiplier) {
        int[] initialFft = new int[fft.length];
        log.info("Starting FFT, {} phases, fft size: {}", phases, fft.length);

        // Extend the fft, not with multiplier times, but the minimum needed (as the pattern repeats)
        int maxIterationsNeeded = 4 * fft.length;
        int[] extendedFft = new int[maxIterationsNeeded];
        for (int i = 0; i < fft.length; i++) {
            extendedFft[i] = fft[i];
            extendedFft[i + fft.length] = fft[i];
            extendedFft[i + fft.length * 2] = fft[i];
            extendedFft[i + fft.length * 3] = fft[i];
        }

        log.info("Extended FFT, fft size: {}", extendedFft.length);

        // Loop through all phases
        for (int phase = 0; phase < phases; phase++) {
            log.info("Running FFT, phase {}", phase);
            //List<Integer> nextValues = new ArrayList<>();
            int[] nextValues = new int[extendedFft.length];

            // For each phase, go through all rows
            for (int row = 0; row < extendedFft.length; row++) {

                // For each row, go through all positions in the list
                int value = 0;
                for (int fftIndex = 0; fftIndex < extendedFft.length; fftIndex++) {
                    value += pattern(extendedFft[fftIndex], fftIndex, row);
                }
                // Adjust for the multiplier
                int multipliedValue = value * (multiplier / maxIterationsNeeded);
                // log.info("row {}, value {}, multipled value {}", row, value, multipliedValue);
                nextValues[row] = Math.abs(multipliedValue % 10);
            }
            extendedFft = nextValues;
            //log.info("After {} phase, {}", phase, extendedFft);
            //log.info("After {} phase", phase);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            sb.append(extendedFft[i]);
        }
        return sb.toString();
    }

    String partTwo() {
        return null;
    }
}

// del två
// går inte att bara kopiera input 10000 ggr, utan något smartare behövs. Förmodligen något med att det finns något
// cykliskt i det hela... Eller går det att räkna ut

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

// matriser

// 1 2 3 4 5 6 7 8    + 0 - 0 + 0 - 0
// 1 2 3 4 5 6 7 8    0 + + 0 0 - - 0
// 1 2 3 4 5 6 7 8    0 0 + + + 0 0 0
// 1 2 3 4 5 6 7 8    0 0 0 + + + + 0
// 1 2 3 4 5 6 7 8    0 0 0 0 + + + +
// 1 2 3 4 5 6 7 8    0 0 0 0 0 + + +
// 1 2 3 4 5 6 7 8    0 0 0 0 0 0 + +
// 1 2 3 4 5 6 7 8    0 0 0 0 0 0 0 +