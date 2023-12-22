package aoc.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day12HotSprings {
    final List<Spring> springs = new ArrayList<>();

    Day12HotSprings(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s = line.split(" ");
            var spring = new Spring();
            spring.condition = s[0];
            for (String t : s[1].split(",")) {
                spring.groups.add(Integer.parseInt(t));
            }
            springs.add(spring);
        }
    }

    int problem1() {
        return springs.stream().mapToInt(Spring::numberOfArrangements).sum();
    }

    int problem2() {
        return 0;
    }

    @Data
    static class Spring {
        String condition;
        final List<Integer> groups = new ArrayList<>();

        int numberOfArrangements() {
            return numberOfArrangements(this);
        }

        int numberOfArrangements(Spring spring) {
            int sum = 0;
            // take the first group and try all possible positions
            int group = spring.groups.get(0);
            // valid strings are .group. ?group. .group? ?group?
            String base = "#".repeat(group);
            if (base.equals(spring.condition)) {
                log.info("### Direct match: {}, {}", spring, this);
                return sum + 1;
            }
            List<String> possible = List.of(base + ".", base + "?", "?".repeat(group) + ".", "?".repeat(group + 1));


            // regex match instead: [\?#]{" + base +"}[\.\?]?"
            var pattern = Pattern.compile("^[#]{" + base.length() +"}[.?]?");
            for (int i = 0; i < spring.condition.length() - base.length(); i++) {
                for (String p : possible) {
                    log.info("checking {} on {} [{}]", p, spring.condition.substring(i), spring.condition);
                    String pp = spring.condition.substring(i, i + p.length());
                    log.info("regex: {}, {}", pattern.pattern(), pp);
                    var matcher = pattern.matcher(spring.condition.substring(i, i + p.length()));
                    if (matcher.find()) {
                        log.info("*** match!!");
                    }

                    if (spring.condition.substring(i).startsWith(p)) {
                        // fits. If it's the last in group return, otherwise continue
                        if (spring.groups.size() == 1) {
                            log.info("### Match: {}, {}", spring, this);
                            sum += 1;
                        } else {
                            var newSpring = new Spring();
                            newSpring.condition = spring.condition.substring(i + p.length());
                            newSpring.groups.addAll(spring.groups.subList(1, spring.groups.size()));
                            log.info("Stepping in, new spring: {}", newSpring);
                            sum += numberOfArrangements(newSpring);
                        }
                    }
                }
            }
            return sum;
        }

        @Override
        public String toString() {
            return condition + ':' + groups;
        }
    }

}
