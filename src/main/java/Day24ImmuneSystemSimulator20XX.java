import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day24ImmuneSystemSimulator20XX {

    enum Side {immuneSystem, infection}

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

        int effectivePower() {
            return units * damage;
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
            return side + "/u=" + units + "/h=" + hitPoints + "/i=" + initiative + "/d=" + damage + "/" + attackType + "/" + weaknesses + "/" + immunities;
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

        Group getDefender(Group attacker) {
            Battle battle = battles.stream().filter(b -> b.attacker == attacker).findFirst().get();
            return battle.defender;
        }
    }


    private Set<Group> groups = new HashSet<>();

    public Day24ImmuneSystemSimulator20XX(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        Pattern pattern = Pattern.compile("^(\\d+) units each with (\\d+) hit points \\((.*)\\) with an attack that does (\\d+) (\\w+) damage at initiative (\\d+)$");
        Pattern weaknessPattern = Pattern.compile("weak to ([\\w, ]*)");
        Pattern immunePattern = Pattern.compile("immune to ([\\w, ]*)");
        Side activeSide = null;
        int numberOfSystems = 0;
        String name = null;

        for (String row : inputStrings) {
            if (row.equals("Immune System:")) {
                activeSide = Side.immuneSystem;
                numberOfSystems = 0;
            } else if (row.equals("Infection:")) {
                activeSide = Side.infection;
                numberOfSystems = 0;
            } else {
                Matcher matcher = pattern.matcher(row);
                if (matcher.find()) {
                    int units = Integer.parseInt(matcher.group(1));
                    int hitPoints = Integer.parseInt(matcher.group(2));
                    String modifiers = matcher.group(3);

                    Set<String> weaknesses = new HashSet<>();
                    Matcher weaknessMatcher = weaknessPattern.matcher(modifiers);
                    if (weaknessMatcher.find()) {
                        weaknesses.addAll(Arrays.asList(weaknessMatcher.group(1).trim().split("\\s*,\\s*")));
                    }

                    Set<String> immunities = new HashSet<>();
                    Matcher immuneMatcher = immunePattern.matcher(modifiers);
                    if (immuneMatcher.find()) {
                        immunities.addAll(Arrays.asList(immuneMatcher.group(1).trim().split("\\s*,\\s*")));
                    }

                    int damage = Integer.parseInt(matcher.group(4));
                    String attackType = matcher.group(5);
                    int initiative = Integer.parseInt(matcher.group(6));

                    numberOfSystems++;
                    groups.add(new Group(numberOfSystems, activeSide, units, hitPoints, initiative, damage, attackType, weaknesses, immunities));
                }
            }
        }
    }

    int winningArmyUnits() {
        do {
            OrderOfBattle oob = targetSelection();
            attack(oob);
            // Remove dead units - does not work...
            //groups.removeIf(g -> g.units == 0);

        } while (groups.stream().filter(g -> g.side == Side.immuneSystem).mapToInt(Group::getUnits).sum() > 0 &&
                groups.stream().filter(g -> g.side == Side.infection).mapToInt(Group::getUnits).sum() > 0);

        return groups.stream().mapToInt(Group::getUnits).sum();
    }

    private OrderOfBattle targetSelection() {
        OrderOfBattle orderOfBattle = new OrderOfBattle();

        // Print stats
        System.out.println("Immune System:");
        groups.stream().filter(g -> g.getSide() == Side.immuneSystem).sorted(Comparator.comparing(Group::getId)).forEach(g -> {
            System.out.println("Group " + g.id + " contains " + g.units + " units");
        });
        System.out.println("Infection:");
        groups.stream().filter(g -> g.getSide() == Side.infection).sorted(Comparator.comparing(Group::getId)).forEach(g -> {
            System.out.println("Group " + g.id + " contains " + g.units + " units");
        });
        System.out.println();

        groups.stream().sorted(Comparator
                .comparing(Group::effectivePower).reversed()
                .thenComparing(Group::getInitiative))
                .forEach(attacker -> {
                    //System.out.println("EP: " + attacker.effectivePower() + " Group: " + attacker);
                    // Chose enemy to attack. Should not have been targeted for attack earlier.
                    // order by most damage, then largest effective power, then highest initiative
                    Optional<Group> defender = groups.stream().filter(g -> g.getSide() != attacker.getSide())
                            .filter(orderOfBattle::isUnattacked).max(Comparator.comparing(attacker::computeDamage)
                                    .thenComparing(Group::effectivePower)
                                    .thenComparing(Group::getInitiative));
                    defender.ifPresent(d -> {
                        orderOfBattle.addAttack(new Battle(attacker, d));
                        System.out.println(attacker.side + " group " + attacker.id + " would deal defending group " + d.id + " " + attacker.computeDamage(d) + " damage");
                    });

//                    for (Group d : groups) {
//                        System.out.println("---Damage: " + attacker.computeDamage(d) + ", defender: " + d);
//                    }
                });
        System.out.println();

        return orderOfBattle;
    }

    private void attack(OrderOfBattle orderOfBattle) {
        groups.stream().sorted(Comparator.comparing(Group::getInitiative).reversed()).forEach(attacker -> {
            Group defender = orderOfBattle.getDefender(attacker);
            int damage = attacker.computeDamage(defender);
//            System.out.println("Attacker: " + attacker + ", defender: " + defender);
            int killedUnits = defender.takeDamage(damage);
//            System.out.println("After the attack, defender took " + damage + " damage: " + defender);
            System.out.println(attacker.side + " group " + attacker.id + " attacks defending group " + defender.id + ", killing " + killedUnits + " units");
        });
    }

}
