package aoc.aoc2015;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day21RpgSimulator20xx {
    final List<Weapon> weapons = new ArrayList<>();
    final List<Armor> armors = new ArrayList<>();
    final List<Ring> rings = new ArrayList<>();

    public Day21RpgSimulator20xx() {
        weapons.add(new Weapon("Dagger", 8, 4, 0));
        weapons.add(new Weapon("Shortsword", 10, 5, 0));
        weapons.add(new Weapon("Warhammer", 25, 6, 0));
        weapons.add(new Weapon("Longsword", 40, 7, 0));
        weapons.add(new Weapon("Greataxe", 74, 8, 0));

        armors.add(new Armor("No armor", 0, 0, 0));
        armors.add(new Armor("Leather", 13, 0, 1));
        armors.add(new Armor("Chainmail", 31, 0, 2));
        armors.add(new Armor("Splintmail", 53, 0, 3));
        armors.add(new Armor("Bandedmail", 75, 0, 4));
        armors.add(new Armor("Platemail ", 102, 0, 5));

        rings.add(new Ring("no ring 1", 0, 0, 0));
        rings.add(new Ring("no ring 2", 0, 0, 0));
        rings.add(new Ring("Damage +1", 25, 1, 0));
        rings.add(new Ring("Damage +2", 50, 2, 0));
        rings.add(new Ring("Damage +3", 100, 3, 0));
        rings.add(new Ring("Defense +1", 20, 0, 1));
        rings.add(new Ring("Defense +2", 40, 0, 2));
        rings.add(new Ring("Defense +3", 80, 0, 3));
    }

    public int problem1() {
        int lowestCost = Integer.MAX_VALUE;

        // Generate all player combinations
        for (Weapon weapon : weapons) {
            for (Armor armor : armors) {
                for (Ring ring1 : rings) {
                    for (Ring ring2 : rings) {
                        if (ring1 != ring2) {
                            List<Ring> ringList = List.of(ring1, ring2);
                            Player player = new Player(100, weapon, armor, ringList);
                            Player boss = new Player(109, 8, 2);
                            if (fight(player, boss) == player) {
                                if (player.cost < lowestCost) {
                                    lowestCost = player.cost;
                                }
                            }
                        }
                    }
                }
            }
        }
        return lowestCost;
    }

    public int problem2() {
        int highestCost = Integer.MIN_VALUE;

        // Generate all player combinations
        for (Weapon weapon : weapons) {
            for (Armor armor : armors) {
                for (Ring ring1 : rings) {
                    for (Ring ring2 : rings) {
                        if (ring1 != ring2) {
                            List<Ring> ringList = List.of(ring1, ring2);
                            Player player = new Player(100, weapon, armor, ringList);
                            Player boss = new Player(109, 8, 2);
                            if (fight(player, boss) == boss) {
                                if (player.cost > highestCost) {
                                    highestCost = player.cost;
                                }
                            }
                        }
                    }
                }
            }
        }
        return highestCost;
    }

    Player fight(Player player, Player boss) {

        while (player.hitPoints > 0 && boss.hitPoints > 0) {
            // Player fights first
            int damage = player.damage - boss.armor;
            if (damage < 1) {
                damage = 1;
            }
            boss.hitPoints -= damage;
            //log.info("The player deals {}-{} = {} damage; the boss goes down to {} hit points", player.damage, boss.armor, damage, boss.hitPoints);

            if (boss.hitPoints > 0) {
                damage = boss.damage - player.armor;
                if (damage < 1) {
                    damage = 1;
                }
                player.hitPoints -= damage;
                //log.info("The boss deals {}-{} = {} damage; the player goes down to {} hit points", boss.damage, player.armor, damage, player.hitPoints);
            }
        }
        if (player.hitPoints > 0) {
            return player;
        } else {
            return boss;
        }
    }

    static class Equipment {
        final String name;
        final int cost;
        final int damage;
        final int armor;

        public Equipment(String name, int cost, int damage, int armor) {
            this.name = name;
            this.cost = cost;
            this.damage = damage;
            this.armor = armor;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class Weapon extends Equipment {
        public Weapon(String name, int cost, int damage, int armor) {
            super(name, cost, damage, armor);
        }
    }

    static class Armor extends Equipment {
        public Armor(String name, int cost, int damage, int armor) {
            super(name, cost, damage, armor);
        }
    }

    static class Ring extends Equipment {
        public Ring(String name, int cost, int damage, int armor) {
            super(name, cost, damage, armor);
        }
    }

    static class Player {
        int hitPoints;
        final int damage;
        final int armor;
        final int cost;

        public Player(int hitPoints, int damage, int armor) {
            this.hitPoints = hitPoints;
            this.damage = damage;
            this.armor = armor;
            this.cost = 0;
        }

        public Player(int hitPoints, Weapon weapon, Armor armor, List<Ring> rings) {
            this.hitPoints = hitPoints;
            this.damage = weapon.damage + rings.stream().mapToInt(r -> r.damage).sum();
            this.armor = armor.armor + rings.stream().mapToInt(r -> r.armor).sum();
            this.cost = weapon.cost + armor.cost + rings.stream().mapToInt(r -> r.cost).sum();
        }
    }
}
