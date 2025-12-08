import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day5Cafeteria {
    RangeSet<Long> rangeSet = TreeRangeSet.create();
    List<Long> ingredients = new ArrayList<>();

    public Day5Cafeteria(List<String> inputLines) {
        var rangePattern = Pattern.compile("(\\d+)-(\\d+)");
        var ingredientPattern = Pattern.compile("(\\d+)");
        List<Range<Long>> ranges = new ArrayList<>();

        for (String line : inputLines) {
            var rangeMatcher = rangePattern.matcher(line);
            if (rangeMatcher.matches()) {
                ranges.add(Range.closed(
                        Long.parseLong(rangeMatcher.group(1)),
                        Long.parseLong(rangeMatcher.group(2))));
            } else {
                var ingredientMatcher = ingredientPattern.matcher(line);
                if (ingredientMatcher.matches()) {
                    ingredients.add(Long.parseLong(ingredientMatcher.group(1)));
                }
            }
        }
        rangeSet.addAll(ranges);
    }

    public long problem1() {
        return ingredients.stream()
                .filter(rangeSet::contains)
                .count();
    }

    public long problem2() {
        return rangeSet.asRanges().stream()
                .map(r -> r.upperEndpoint() - r.lowerEndpoint() + 1)
                .mapToLong(Long::longValue)
                .sum();
    }
}
