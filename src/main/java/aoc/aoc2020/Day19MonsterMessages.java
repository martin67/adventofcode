package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day19MonsterMessages {
    List<Rule> rules = new ArrayList<>();
    //Map<Integer, Rule> rules = new HashMap<>();
    List<String> messages = new ArrayList<>();

    public Day19MonsterMessages(List<String> inputLines) {
        Pattern noRulePattern = Pattern.compile("^(\\d+): \"(\\w)\"$");
        Pattern singleRulePattern = Pattern.compile("^(\\d+):(( \\d+)+)$");
        Pattern twoRulePattern = Pattern.compile("^(\\d+):(.*)\\|(.*)$");
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
                for (String s : matcher.group(2).trim().split(" ")) {
                    addToRuleList(ruleList, Integer.parseInt(s));
                }
                rule.possibleRules.add(ruleList);
                ruleList = new ArrayList<>();
                for (String s : matcher.group(3).trim().split(" ")) {
                    addToRuleList(ruleList, Integer.parseInt(s));
                }
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
            Rule firstRule = getRule("0");
            String result = firstRule.match(message);
            if (result != null && result.isEmpty()) {
                log.info("--Message {} matched!", message);
                numberOfMatches++;
            } else {
                log.info("--Message {} did not match", message);
            }
        }
        return numberOfMatches;
    }

    int problem2() {
        Rule rule = getRule("8");
        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(getRule("42"));
        ruleList.add(getRule("8"));
        rule.possibleRules.add(ruleList);

        rule = getRule("11");
        ruleList = new ArrayList<>();
        ruleList.add(getRule("42"));
        ruleList.add(getRule("11"));
        ruleList.add(getRule("31"));
        rule.possibleRules.add(ruleList);
        return problem1();
    }

    static class Rule {
        int id;
        char c = '-';
        List<List<Rule>> possibleRules = new ArrayList<>();
        Set<String> cache = new HashSet<>();

        public Rule(int id) {
            this.id = id;
        }

        String match(String in) {
            log.info("Starting match on rule {} for string {}", id, in);

            if (in.isEmpty()) {
                return null;
            }

            // Check cache for all lengths of in.
            for (int i = 0; i < in.length(); i++) {
                String key = in.substring(0, i + 1);
                //String key = in.substring(0, in.length() - i);
                if (cache.contains(key)) {
                    String res = in.substring(i + 1);
                    log.info("Cache hit: rule {}, in: {}, key: {}, out: {}", id, in, key, res);
                    return res;
                }
            }

            if (in.charAt(0) == c) {
                log.debug("Character match rule {}: in: {}, out:{}", id, in, in.substring(1));
                return in.substring(1);
            } else if (c != '-') {
                log.debug("No character match rule {}: in: {}", id, in);
                return null;
            } else {
                String newMatch = null;
                for (List<Rule> orRules : possibleRules) {
                    String andResult = in;
                    for (Rule andRule : orRules) {
                        if (andResult != null) {
                            andResult = andRule.match(andResult);
                        }
                    }
                    if (andResult != null) {
                        newMatch = andResult;
                        break;
                    }
                }
                log.debug("Match rule {}: in: {}, out:{}", id, in, newMatch);
                if (newMatch != null) {
                    if (newMatch.isEmpty()) {
                        log.info("Add cache rule {}: {}, key: {}", id, in, in);
                        cache.add(in);
                    } else if (newMatch.length() > 1) {
                        String key = in.substring(0, in.length() - newMatch.length());
                        log.info("Add cache rule {}: in: {}, remaining: {}, key: {}", id, in, newMatch, key);
                        cache.add(key);
                    }
                }
                return newMatch;
            }

        }
    }
}
