package aoc.common;

import java.util.Arrays;
import java.util.List;

public class AocMath {

    private AocMath() {
    }

    public static long gcd(long x, long y) {
        return (y == 0) ? x : gcd(y, x % y);
    }

    public static long lcm(long... numbers) {
        return Arrays.stream(numbers).reduce(1, (x, y) -> x * (y / gcd(x, y)));
    }

    public static long lcm(List<Long> numbers) {
        return numbers.stream().reduce(1L, (x, y) -> x * (y / gcd(x, y)));
    }

}
