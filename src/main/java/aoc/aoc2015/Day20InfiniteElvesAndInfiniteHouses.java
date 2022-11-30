package aoc.aoc2015;

import java.util.ArrayList;
import java.util.List;

public class Day20InfiniteElvesAndInfiniteHouses {

    public long lowestHouseNumber(long input) {
        return splitNumber(input).stream().mapToLong(l -> l).sum() * 10;
    }

    public List<Long> splitNumber(long n) {
        List<Long> factors = new ArrayList<>();
        for (long i = 1; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                i++;
            }
        }
        return factors;
    }

    public long lowestHouseNumber2(long input) {
        long houseNumber = 1;
        long presents;
        do {
            presents = lowestHouseNumber(houseNumber);
            houseNumber++;
        } while (presents < input);
        return houseNumber;
    }
}
