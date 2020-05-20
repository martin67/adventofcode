package aoc.aoc2016;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

@Slf4j
public class Day20FirewallRules {

    TreeSet<Range<Long>> ranges = new TreeSet<>(Comparator.comparing(Range::lowerEndpoint));

    public Day20FirewallRules(List<String> inputLines) {
        for (String row : inputLines) {
            String[] number = row.split("-");
            Range<Long> newRange = Range.closed(Long.parseLong(number[0]), Long.parseLong(number[1]));

            Iterator<Range<Long>> it = ranges.iterator();
            while (it.hasNext()) {
                Range<Long> range = it.next();
                if (newRange.isConnected(range)) {
                    newRange = newRange.span(range);
                    it.remove();
                }
            }
            ranges.add(newRange);

        }
    }

    long lowestValuedIp() {

        Iterator<Range<Long>> it = ranges.iterator();
        Range<Long> previousRange = it.next();
        while (it.hasNext()) {
            Range<Long> range = it.next();
            if (previousRange.upperEndpoint() + 1 != range.lowerEndpoint()) {
                log.info("Found first empty spot {}", previousRange.upperEndpoint() + 1);
                break;
            } else {
                previousRange = range;
            }
        }
        return previousRange.upperEndpoint() + 1;
    }

}
