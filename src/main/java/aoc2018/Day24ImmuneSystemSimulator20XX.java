package aoc2018;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class Day24ImmuneSystemSimulator20XX {

    enum Side {
        IMMUNE_SYSTEM {
            @Override
            public String toString() {
                return "Immune System";
            }
        }, INFECTION {
            @Override
            public String toString() {
                return "Infection";
            }
        }
    }

    @Data
    @AllArgsConstructor
    static class Group {
        int id;
        Side side;
        int units;
        int hitPoints;
        int initiative;
        int damage;
        String attackType;
        Set<String> weaknesses;
        Set<String> immunities;
        int boost;

        Group(Group group) {
            id = group.id;
            side = group.side;
            units = group.units;
            hitPoints = group.hitPoints;
            initiative = group.initiative;
            damage = group.damage;
            attackType = group.attackType;
            weaknesses = group.weaknesses;
            immunities = group.immunities;
            boost = group.boost;
        }

        int effectivePower() {
            return units * (damage + boost);
        }

        int computeDamage(Group opponent) {
            int damage = this.effectivePower();
            if (opponent.getImmunities().contains(this.attackType)) {
                damage = 0;
            } else if (opponent.getWeaknesses().contains(this.attackType)) {
                damage *= 2;
            }
            return damage;
        }

        int takeDamage(int d) {
            int killedUnits = d / hitPoints;
            if (killedUnits < units) {
                units -= killedUnits;
            } else {
                killedUnits = units;
                units = 0;
            }
            return killedUnits;
        }

        @Override
        public String toString() {
            return side + "/id=" + id + "/u=" + units + "/h=" + hitPoints + "/i=" + initiative + "/d=" + damage + "/" + attackType + "/" + weaknesses + "/" + immunities;
        }
    }

    @Data
    @AllArgsConstructor
    static class Battle {
        Group attacker;
        Group defender;
    }

    @Data
    static class OrderOfBattle {
        List<Battle> battles = new ArrayList<>();

        void addAttack(Battle a) {
            battles.add(a);
        }

        boolean isUnattacked(Group g) {
            for (Battle b : battles) {
                if (b.defender == g) {
                    return false;
                }
            }
            return true;
        }

        Optional<Group> getDefender(Group attacker) {
            Optional<Battle> battle = battles.stream().filter(b -> b.attacker == attacker).findFirst();
            return battle.map(d -> d.defender);
        }
    }

    private List<Group> groups = new ArrayList<>();

    public Day24ImmuneSystemSimulator20XX(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        Pattern pattern = Pattern.compile("^(?<units>\\d+) units each with (?<hitPoints>\\d+) hit points (?<modifiers>\\((.*)\\) )?with an attack that does (?<damage>\\d+) (?<attackType>\\w+) damage at initiative (?<initiative>\\d+)$");
        Pattern weaknessPattern = Pattern.compile("weak to ([\\w, ]*)");
        Pattern immunePattern = Pattern.compile("immune to ([\\w, ]*)");
        Side activeSide = null;
        int numberOfSystems = 0;

        for (String row : inputStrings) {
            if (row.equals("Immune System:")) {
                activeSide = Side.IMMUNE_SYSTEM;
                numberOfSystems = 0;
            } else if (row.equals("Infection:")) {
                activeSide = Side.INFECTION;
                numberOfSystems = 0;
            } else {
                Matcher matcher = pattern.matcher(row);
                if (matcher.find()) {
                    int units = Integer.parseInt(matcher.group("units"));
                    int hitPoints = Integer.parseInt(matcher.group("hitPoints"));

                    Set<String> weaknesses = new HashSet<>();
                    Set<String> immunities = new HashSet<>();
                    String modifiers = matcher.group("modifiers");
                    if (modifiers != null) {
                        Matcher weaknessMatcher = weaknessPattern.matcher(modifiers);
                        if (weaknessMatcher.find()) {
                            weaknesses.addAll(Arrays.asList(weaknessMatcher.group(1).trim().split("\\s*,\\s*")));
                        }

                        Matcher immuneMatcher = immunePattern.matcher(modifiers);
                        if (immuneMatcher.find()) {
                            immunities.addAll(Arrays.asList(immuneMatcher.group(1).trim().split("\\s*,\\s*")));
                        }
                    }

                    int damage = Integer.parseInt(matcher.group("damage"));
                    String attackType = matcher.group("attackType");
                    int initiative = Integer.parseInt(matcher.group("initiative"));

                    numberOfSystems++;
                    groups.add(new Group(numberOfSystems, activeSide, units, hitPoints, initiative, damage, attackType, weaknesses, immunities, 0));
                }
            }
        }
    }

    int winningArmyUnits() {
        int round = 1;
        List<Group> groupList = groups;

        do {
            System.out.printf("Round: %d\n----------------------------\n", round);
            OrderOfBattle oob = targetSelection(groupList);
            attack(groupList, oob);

            // Remove dead units
            groupList.removeIf(g -> g.units == 0);

            round++;
        } while (groupList.stream().filter(g -> g.side == Side.IMMUNE_SYSTEM).mapToInt(Group::getUnits).sum() > 0 &&
                groupList.stream().filter(g -> g.side == Side.INFECTION).mapToInt(Group::getUnits).sum() > 0);

        return groupList.stream().mapToInt(Group::getUnits).sum();
    }

    int immuneSystemUnitsLeft() {
        int boost = 49; // trial and error. The original stopped for numbers between 42 and 48

        while (true) {
            List<Group> groupList = groups.stream().map(Group::new).collect(toList());

            System.out.print("Setting boost to: " + boost);
            setImmuneBoost(groupList, boost);

            do {
                OrderOfBattle oob = targetSelection(groupList);
                attack(groupList, oob);

                // Remove dead units
                groupList.removeIf(g -> g.units == 0);

            } while (groupList.stream().filter(g -> g.side == Side.IMMUNE_SYSTEM).mapToInt(Group::getUnits).sum() > 0 &&
                    groupList.stream().filter(g -> g.side == Side.INFECTION).mapToInt(Group::getUnits).sum() > 0);

            if (groupList.stream().filter(g -> g.side == Side.IMMUNE_SYSTEM).mapToInt(Group::getUnits).sum() > 0) {
                System.out.println(" -- Immune system victorious!");
                return groupList.stream().mapToInt(Group::getUnits).sum();
            } else {
                System.out.println(" -- Infection victory");
            }
            boost++;
        }
    }

    private void setImmuneBoost(List<Group> groupList, int boost) {
        groupList.stream().filter(g -> g.side == Side.IMMUNE_SYSTEM).forEach(g -> g.setBoost(boost));
    }

    private OrderOfBattle targetSelection(List<Group> groupList) {
        OrderOfBattle orderOfBattle = new OrderOfBattle();

        // Print stats
//        for (Side side : Side.values()) {
//            System.out.println(side + ":");
//            groupList.stream().filter(g -> g.getSide() == side)
//                    .sorted(Comparator.comparing(Group::getId))
//                    .forEach(g -> System.out.println("Group " + g.id + " contains " + g.units + " units"));
//        }
//        System.out.println();

        // Choose attacker in descending order of effective power, then initiative
        groupList.stream().sorted(Comparator
                .comparing(Group::effectivePower)
                .thenComparing(Group::getInitiative).reversed())
                .forEach(attacker -> {
                    // System.out.println("Attacker: " + attacker + ", EP: " + attacker.effectivePower());
                    // Chose enemy to attack. Should not have been targeted for attack earlier.
                    // order by most damage, then largest effective power, then highest initiative
                    Optional<Group> defender = groupList.stream()
                            .filter(g -> g.getSide() != attacker.getSide())
                            .filter(orderOfBattle::isUnattacked)
                            .max(Comparator
                                    .comparing(attacker::computeDamage)
                                    .thenComparing(Group::effectivePower)
                                    .thenComparing(Group::getInitiative));
                    defender.ifPresent(d -> {
                        // "If it cannot deal any defending groups damage, it does not choose a target."
                        if (attacker.computeDamage(d) > 0) {
                            orderOfBattle.addAttack(new Battle(attacker, d));
                            //System.out.println("## " + attacker.side + " group " + attacker.id + " would deal defending group " + d.id + " " + attacker.computeDamage(d) + " damage");
                        }
                    });

//                    for (Group d : groupList) {
//                        if (d.side != attacker.side) {
//                            System.out.println("---Damage: " + attacker.computeDamage(d) + ", defender: " + d + ", EP: " + d.effectivePower() + ", Unattacked: " + orderOfBattle.isUnattacked(d));
//                        }
//                    }
                });

//        for (Side side : Side.values()) {
//            orderOfBattle.stream()
//                    .filter(b -> b.attacker.side == side)
//                    .sorted(Comparator.comparing(b -> b.attacker.getId()))
//                    .forEach(b -> System.out.println(b.attacker.side + " group " + b.attacker.id + " would deal defending group " + b.defender.id + " " + b.attacker.computeDamage(b.defender) + " damage"));
//        }
//        System.out.println();

        return orderOfBattle;
    }

    private void attack(List<Group> groupList, OrderOfBattle orderOfBattle) {
        // Groups attack in decreasing order of initiative
        groupList.stream().sorted(Comparator.comparing(Group::getInitiative).reversed()).forEach(attacker -> {
            Optional<Group> defender = orderOfBattle.getDefender(attacker);
            defender.ifPresent(def -> {
                int damage = attacker.computeDamage(def);
//                int killedUnits = def.takeDamage(damage);
//                System.out.println(attacker.side + " group " + attacker.id + " attacks defending group " + def.id + ", killing " + killedUnits + " units");
            });
        });
        //System.out.println();
    }

}
