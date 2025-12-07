import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class Day2GiftShop {
    List<ProductId> productIds = new ArrayList<>();

    Day2GiftShop(List<String> inputLines) {
        for (String line : inputLines) {
            String[] ss = line.split(",");
            for (String s : ss) {
                productIds.add(new ProductId(s));
            }
        }
    }

    long problem1() {
        return productIds.stream()
                .flatMap(productId -> productId.invalidIds().stream())
                .mapToLong(Long::longValue)
                .sum();
    }

    long problem2() {
        return productIds.stream()
                .flatMap(productId -> productId.invalidIds2().stream())
                .mapToLong(Long::longValue)
                .sum();
    }

    static class ProductId {
        long firstId;
        long lastId;

        public ProductId(String id) {
            String[] s = id.split("-");
            this.firstId = Long.parseLong(s[0]);
            this.lastId = Long.parseLong(s[1]);
        }

        List<Long> invalidIds() {
            List<Long> invalidIds = new ArrayList<>();
            // adjust so that first and last has a multiple of two number of digits
            long lower = firstId;
            if (String.valueOf(firstId).length() % 2 != 0) {
                lower = (long) Math.pow(10, String.valueOf(firstId).length());
            }
            long upper = lastId;
            if (String.valueOf(lastId).length() % 2 != 0) {
                upper = (long) Math.pow(10, String.valueOf(firstId).length()) - 1;
            }
            for (long i = lower; i <= upper; i++) {
                String number = String.valueOf(i);
                String left = number.substring(0, number.length() / 2);
                String right = number.substring(number.length() / 2);
                if (left.equals(right)) {
                    invalidIds.add(i);
                }
            }
            return invalidIds;
        }

        List<Long> invalidIds2() {
            List<Long> invalidIds = new ArrayList<>();

            for (long i = firstId; i <= lastId; i++) {
                String number = String.valueOf(i);
                var divisors = getDivisors(number.length());
                for (int divisor : divisors) {
                    String left = number.substring(0, divisor);
                    String leftSearch = left.repeat((number.length() - left.length()) / divisor);
                    String right = number.substring(divisor);

                    if (leftSearch.equals(right)) {
                        invalidIds.add(i);
                        break;
                    }
                }
            }
            return invalidIds;
        }

        private List<Integer> getDivisors(int n) {
            List<Integer> result = IntStream.rangeClosed(1, (int) Math.sqrt(n))
                    .filter(i -> n % i == 0)
                    .boxed()
                    .flatMap(i -> i == n / i ? Stream.of(i) : Stream.of(i, n / i))
                    .sorted()
                    .toList();

            return result.subList(0, result.size() - 1);  // drop last
        }

    }
}
