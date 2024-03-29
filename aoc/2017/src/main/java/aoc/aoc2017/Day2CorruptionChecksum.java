package aoc.aoc2017;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Day2CorruptionChecksum {

    int computeChecksum(List<String> spreadsheet) {
        int checkSum = 0;
        for (String row : spreadsheet) {
            int max = Stream.of(row.split("\\s")).mapToInt(Integer::parseInt).max().orElseThrow();
            int min = Stream.of(row.split("\\s")).mapToInt(Integer::parseInt).min().orElseThrow();
            checkSum += max - min;
        }
        return checkSum;
    }

    int computeDivisibleChecksum(List<String> spreadsheet) {
        int checkSum = 0;
        for (String row : spreadsheet) {
            List<Integer> sortedRow = Stream.of(row.split("\\s"))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .sorted(Comparator.reverseOrder()).toList();
            for (Integer numerator : sortedRow) {
                List<Integer> sortedRowWithoutNumerator = new ArrayList<>(sortedRow);
                sortedRowWithoutNumerator.remove(numerator);
                for (Integer denominator : sortedRowWithoutNumerator) {
                    if (numerator % denominator == 0) {
                        checkSum += numerator / denominator;
                    }
                }
            }
        }
        return checkSum;
    }
}
