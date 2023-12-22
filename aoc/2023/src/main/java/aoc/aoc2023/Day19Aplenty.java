package aoc.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day19Aplenty {
    final List<Workflow> workflows = new ArrayList<>();
    final List<Part> parts = new ArrayList<>();

    public Day19Aplenty(List<String> inputLines) {
        var workflowPattern = Pattern.compile("(\\w+)\\{(.*)}");
        var partPattern = Pattern.compile("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}");

        for (String line : inputLines) {
            var matcher = workflowPattern.matcher(line);
            if (matcher.find()) {
                var workflow = new Workflow();
                workflow.name = matcher.group(1);
                for (String r : matcher.group(2).split(",")) {
                    var rule = new Rule();
                    if (r.contains("<") || r.contains(">")) {
                        rule.category = r.substring(0, 1);
                        rule.condition = r.substring(1, 2);
                        String[] s = r.substring(2).split(":");
                        rule.value = Integer.parseInt(s[0]);
                        rule.target = s[1];
                    } else {
                        rule.target = r;
                    }
                    workflow.rules.add(rule);
                }
                workflows.add(workflow);
            }

            matcher = partPattern.matcher(line);
            if (matcher.find()) {
                parts.add(new Part(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4))));
            }
        }
    }

    public int problem1() {
        int sum = 0;
        for (var part : parts) {
            String target = "";
            var workflow = workflows.stream().filter(w -> w.name.equals("in")).findFirst().orElseThrow();

            while (!target.matches("[A|R]")) {
                target = workflow.next(part);
                String finalTarget = target;
                workflow = workflows.stream().filter(w -> w.name.equals(finalTarget)).findFirst().orElse(null);
            }
            if (target.equals("A")) {
                sum += part.value();
            }
        }
        return sum;
    }

    public int problem2() {

        // Find all workflow paths to A
        // go through them and fins the smallest x,m,a,s
        // multiply and add

        return 0;
    }

    static class Workflow {
        String name;
        List<Rule> rules = new ArrayList<>();

        String next(Part part) {
            for (var rule : rules) {
                if (rule.category == null) {
                    break;
                }
                if (rule.category.equals("x") && rule.condition.equals(">")) {
                    if (part.x > rule.value) {
                        return rule.target;
                    }
                } else if (rule.category.equals("x") && rule.condition.equals("<")) {
                    if (part.x < rule.value) {
                        return rule.target;
                    }
                } else if (rule.category.equals("m") && rule.condition.equals(">")) {
                    if (part.m > rule.value) {
                        return rule.target;
                    }
                } else if (rule.category.equals("m") && rule.condition.equals("<")) {
                    if (part.m < rule.value) {
                        return rule.target;
                    }
                } else if (rule.category.equals("a") && rule.condition.equals(">")) {
                    if (part.a > rule.value) {
                        return rule.target;
                    }
                } else if (rule.category.equals("a") && rule.condition.equals("<")) {
                    if (part.a < rule.value) {
                        return rule.target;
                    }
                } else if (rule.category.equals("s") && rule.condition.equals(">")) {
                    if (part.s > rule.value) {
                        return rule.target;
                    }
                } else if (rule.category.equals("s") && rule.condition.equals("<")) {
                    if (part.s < rule.value) {
                        return rule.target;
                    }
                }
            }
            return rules.get(rules.size() - 1).target;
        }
    }

    static class Rule {
        String category;
        String condition;
        int value;
        String target;
    }

    record Part(int x, int m, int a, int s) {
        int value() {
            return x + m + a + s;
        }
    }
}
