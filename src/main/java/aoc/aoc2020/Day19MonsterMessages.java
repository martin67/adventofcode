package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day19MonsterMessages {
    List<Rule> rules = new ArrayList<>();
    List<String> messages = new ArrayList<>();

    public Day19MonsterMessages(List<String> inputLines) {
        Pattern noRulePattern = Pattern.compile("^(\\d+): \"(\\w)\"$");
        Pattern singleRulePattern = Pattern.compile("^(\\d+):(( \\d+)+)$");
        Pattern twoRulePattern = Pattern.compile("^(\\d+): (\\d+) (\\d+) \\| (\\d+) (\\d+)$");
        Pattern messagePattern = Pattern.compile("^(\\w+)$");
        Matcher matcher;

        for (String line : inputLines) {
            matcher = noRulePattern.matcher(line);
            if (matcher.find()) {
                Rule rule = getRule(matcher.group(1));
                rule.c = matcher.group(2).charAt(0);
            }
            matcher = singleRulePattern.matcher(line);
            if (matcher.find()) {
                Rule rule = getRule(matcher.group(1));
                List<Rule> ruleList = new ArrayList<>();
                for (String s : matcher.group(2).trim().split(" ")) {
                    addToRuleList(ruleList, Integer.parseInt(s));
                }
                rule.possibleRules.add(ruleList);
            }
            matcher = twoRulePattern.matcher(line);
            if (matcher.find()) {
                Rule rule = getRule(matcher.group(1));
                List<Rule> ruleList = new ArrayList<>();
                addToRuleList(ruleList, Integer.parseInt(matcher.group(2)));
                addToRuleList(ruleList, Integer.parseInt(matcher.group(3)));
                rule.possibleRules.add(ruleList);
                ruleList = new ArrayList<>();
                addToRuleList(ruleList, Integer.parseInt(matcher.group(4)));
                addToRuleList(ruleList, Integer.parseInt(matcher.group(5)));
                rule.possibleRules.add(ruleList);
            }
            matcher = messagePattern.matcher(line);
            if (matcher.find()) {
                messages.add(matcher.group(1));
            }
        }
    }

    Rule getRule(String ruleId) {
        int id = Integer.parseInt(ruleId);
        Optional<Rule> rule = rules.stream().filter(r -> r.id == id).findAny();
        if (rule.isPresent()) {
            return rule.get();
        } else {
            Rule newRule = new Rule(id);
            rules.add(newRule);
            return newRule;
        }
    }

    void addToRuleList(List<Rule> list, int ruleId) {
        Optional<Rule> rule = rules.stream().filter(r -> r.id == ruleId).findAny();
        if (rule.isPresent()) {
            list.add(rule.get());
        } else {
            Rule newRule = new Rule(ruleId);
            rules.add(newRule);
            list.add(newRule);
        }
    }

    int problem1() {
        int numberOfMatches = 0;

        for (String message : messages) {
            log.info("--Checking message: {}", message);
            //           if (match(rules.get(0), "", message)) {
            if (match2(rules.get(0), message, 0) > 0) {
                log.info("--Message {} matched!", message);
                numberOfMatches++;
            } else {
                log.info("--Message {} did not match", message);
            }
        }
        return numberOfMatches;
    }

    int match2(Rule rule, String msg, int index) {
        log.info("Starting match on rule {}, index {} => message {}, rule size: {}",
                rule.id, index, msg.substring(index), rule.possibleRules.size());
        int steps = 0;
        if (msg.charAt(index) == rule.c) {
            log.info("Matched!");
            steps = 1;
        } else if (rule.c == '-') {
            if (rule.possibleRules.size() == 1) {
                int r1;
                int r2;
                int r3;
                switch (rule.possibleRules.get(0).size()) {
                    case 1:
                        log.info("Checking if rule {} on {}",
                                rule.possibleRules.get(0).get(0).id, msg.substring(index));
                        r1 = match2(rule.possibleRules.get(0).get(0), msg, index);
                        if (r1 > 0) {
                            log.info("Matched!");
                            steps = r1;
                        } else {
                            log.info("No match");
                        }
                        break;
                    case 2:
                        log.info("Checking if rule {} AND rule {} match on {} -- size: {}",
                                rule.possibleRules.get(0).get(0).id,
                                rule.possibleRules.get(0).get(1).id, msg.substring(index), rule.possibleRules.get(0).size());
                        r1 = match2(rule.possibleRules.get(0).get(0), msg, index);
                        r2 = match2(rule.possibleRules.get(0).get(0), msg, index + r1);
                        if (r1 > 0 && r2 > 0) {
                            log.info("Matched!");
                            steps = r1 + r2;
                        } else {
                            log.info("No match");
                        }
                        break;
                    case 3:
                        log.info("Checking if rule {} AND rule {} AND rule {} match on {}",
                                rule.possibleRules.get(0).get(0).id,
                                rule.possibleRules.get(0).get(1).id,
                                rule.possibleRules.get(0).get(2).id, msg);
                        r1 = match2(rule.possibleRules.get(0).get(0), msg, index);
                        r2 = match2(rule.possibleRules.get(0).get(1), msg, index + r1);
                        r3 = match2(rule.possibleRules.get(0).get(2), msg, index + r1 + r2);

                        if (r1 > 0 && r2 > 0 && r3 > 0) {
                            log.info("Matched!");
                            steps = r1 + r2 + r3;
                        } else {
                            log.info("No match");
                        }
                        break;
                    default:
                        assert false : "oops";
                }
            } else if (rule.possibleRules.size() == 2) {
                log.info("Checking if (rule {} AND rule {} match) OR (rule {} AND rule {} match) on {}",
                        rule.possibleRules.get(0).get(0).id,
                        rule.possibleRules.get(0).get(1).id,
                        rule.possibleRules.get(1).get(0).id,
                        rule.possibleRules.get(1).get(1).id, msg);
                int r1 = match2(rule.possibleRules.get(0).get(0), msg, index);
                int r2 = match2(rule.possibleRules.get(0).get(1), msg, index + r1);
                int r3 = match2(rule.possibleRules.get(1).get(0), msg, index + r1 + r2);
                int r4 = match2(rule.possibleRules.get(1).get(1), msg, index + r1 + r2 + r3);
                if ((r1 > 0 && r2 > 0)) {
                    log.info("Matched!");
                    steps = r1 + r2;
                } else if (r3 > 0 && r4 > 0) {
                    log.info("Matched!");
                    steps = r3 + r4;
                } else {
                    log.info("No match");
                }
            }
        }
        return steps;
    }


    boolean match(Rule rule, String previous, String msg) {
        String current = previous + "-" + rule.id;
        log.info("Starting match on rule {} for message {}, rule size: {}", current, msg, rule.possibleRules.size());
        boolean result = false;
        if (msg.charAt(0) == rule.c) {
            result = true;
        } else if (rule.c == '-') {
            if (rule.possibleRules.size() == 1) {
                switch (rule.possibleRules.get(0).size()) {
                    case 1:
                        log.info("Checking if rule {} on {}",
                                rule.possibleRules.get(0).get(0).id, msg);
                        if (match(rule.possibleRules.get(0).get(0), current, msg)) {
                            result = true;
                        } else {
                            result = false;
                        }
                        break;
                    case 2:
                        log.info("Checking if rule {} AND rule {} match on {} -- size: {}",
                                rule.possibleRules.get(0).get(0).id,
                                rule.possibleRules.get(0).get(1).id, msg, rule.possibleRules.get(0).size());
                        if (match(rule.possibleRules.get(0).get(0), current, msg) &&
                                match(rule.possibleRules.get(0).get(1), current, msg.substring(1))) {
                            result = true;
                        } else {
                            result = false;
                        }
                        break;
                    case 3:
                        log.info("Checking if rule {} AND rule {} AND rule {} match on {}",
                                rule.possibleRules.get(0).get(0).id,
                                rule.possibleRules.get(0).get(1).id,
                                rule.possibleRules.get(0).get(2).id, msg);
                        if (match(rule.possibleRules.get(0).get(0), current, msg) &&
                                match(rule.possibleRules.get(0).get(1), current, msg.substring(1)) &&
                                match(rule.possibleRules.get(0).get(2), current, msg.substring(2))) {
                            result = true;
                        } else {
                            result = false;
                        }
                        break;
                    default:
                        assert false : "oops";
                }
            } else if (rule.possibleRules.size() == 2) {
                log.info("Checking if (rule {} AND rule {} match) OR (rule {} AND rule {} match) on {}",
                        rule.possibleRules.get(0).get(0).id,
                        rule.possibleRules.get(0).get(1).id,
                        rule.possibleRules.get(1).get(0).id,
                        rule.possibleRules.get(1).get(1).id, msg);
                if ((match(rule.possibleRules.get(0).get(0), current, msg) &&
                        match(rule.possibleRules.get(0).get(1), current, msg.substring(1)) || (
                        match(rule.possibleRules.get(1).get(0), current, msg) &&
                                match(rule.possibleRules.get(1).get(1), current, msg.substring(1))))) {
                    result = true;
                } else {
                    result = false;
                }
            }
        } else {
            result = false;
        }
        log.info("Match rule {}: {}", current, result);
        return result;
    }

    static class Rule {
        int id;
        char c = '-';
        List<List<Rule>> possibleRules = new ArrayList<>();

        public Rule(int id) {
            this.id = id;
        }

        boolean match(char in) {
            log.info("Starting match on rule {} for char {} ", id, in);
            boolean result;
            if (in == c) {
                result = true;
            } else if (c == '-') {
                boolean oneMatch = false;
                for (List<Rule> orRules : possibleRules) {
                    boolean allAndRulesMatch = true;
                    for (Rule andRule : orRules) {
                        if (!andRule.match(in)) {
                            allAndRulesMatch = false;
                        }
                    }
                    if (allAndRulesMatch) {
                        oneMatch = true;
                    }
                }
                result = oneMatch;
            } else {
                result = false;
            }
            log.info("Match rule {}: {}", id, result);
            return result;
        }
    }
}

// 170 too low
// 118 too low