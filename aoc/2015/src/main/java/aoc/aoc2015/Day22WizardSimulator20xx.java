package aoc.aoc2015;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day22WizardSimulator20xx {

    final List<Spell> spells;
    final Spell magicMissile = new Spell("Magic Missile", 53, 4, 0, 0);
    final Spell drain = new Spell("Drain", 73, 2, 2, 0);
    final Spell shield = new Spell("Shield", 113, 0, 0, 6);
    final Spell poison = new Spell("Poison", 173, 0, 0, 6);
    final Spell recharge = new Spell("Recharge", 229, 0, 0, 5);

    public Day22WizardSimulator20xx() {
        spells = List.of(magicMissile, drain, shield, poison, recharge);
    }

    public int problem1() {
        Player winner = playerTurn(new Player(50, 500), new Boss(55, 8));
        return winner.mana;
    }

    public int problem2() {
        return 0;
    }

    // return winner
    Player playerTurn(Player player, Boss boss) {
        log.info("-- Player turn --");
        log.info("- Player has {} hit points, {} armor, {} mana", player.hitPoints, player.armor, player.mana);
        log.info("- Boss has {} hit points", boss.hitPoints);
        Player winner;

        evauluateDurationSpells(player, boss);

        if (boss.hitPoints <= 0) {
            log.info("The boss is dead!");
            return player;
        }

        for (Spell spell : spells) {
            if (spell.cost <= player.mana) {
                player.mana -= spell.cost;
                player.manaSpent += spell.cost;
                switch (spell.name) {
                    case "Magic Missile" -> {
                        log.info("Player casts {}, dealing {} damage.", spell.name, spell.damage);
                        boss.hitPoints -= spell.damage;
                    }
                    case "Drain" -> {
                        log.info("Player casts {}, dealing {} damage, and healing {} hit points.", spell.name, spell.damage, spell.heal);
                        boss.hitPoints -= spell.damage;
                        player.hitPoints += spell.heal;
                    }
                    case "Shield" -> {
                        log.info("Player casts Shield, increasing armor by 7.");
                        player.duration.put(spell, spell.duration);
                        player.armor = 7;
                    }
                    case "Poison" -> {
                        log.info("Player casts Poison.");
                        player.duration.put(spell, spell.duration);
                    }
                    case "Recharge" -> {
                        log.info("Player casts Recharge.");
                        player.duration.put(spell, spell.duration);
                    }
                }

                if (boss.hitPoints > 0) {
                    winner = bossTurn(new Player(player), new Boss(boss), spell);
                } else {
                    log.info("The boss is dead!");
                    winner = player;
                }
            }
        }
        return null;
    }

    private void evauluateDurationSpells(Player player, Boss boss) {
        // Evaluate duration spells
        for (Spell spell : player.duration.keySet()) {
            if (player.duration.get(spell) > 0) {
                player.duration.merge(spell, -1, Integer::sum);
                switch (spell.name) {
                    case "Shield" -> {
                        log.info("Shield's time is now {}.", player.duration.get(spell));
                        player.armor = 7;
                        if (player.duration.get(spell) == 0) {
                            log.info("Shield wears off, decreasing armor by 7.");
                            player.armor = 0;
                        }
                    }
                    case "Poison" -> {
                        log.info("Poison deals 3 damage; its timer is now {}.", player.duration.get(spell));
                        boss.hitPoints -= 3;
                    }
                    case "Recharge" -> {
                        log.info("Recharge provides 101 mana; its timer is now {}.", player.duration.get(spell));
                        player.mana += 101;
                        if (player.duration.get(spell) == 0) {
                            log.info("Recharge wears off.");
                        }
                    }
                }
            }
        }
    }

    Player bossTurn(Player player, Boss boss, Spell spell) {
        log.info("-- Boss turn --");
        log.info("- Player has {} hit points, {} armor, {} mana", player.hitPoints, player.armor, player.mana);
        log.info("- Boss has {} hit points", boss.hitPoints);

        evauluateDurationSpells(player, boss);

        // Boss attack!
        if (player.armor > 0) {
            log.info("Boss attacks for {} - {} = {} damage!", boss.damage, player.armor, boss.damage - player.armor);
            player.hitPoints -= (boss.damage - player.armor);
        } else {
            log.info("Boss attacks for {} damage!", boss.damage);
            player.hitPoints -= boss.damage;
        }

        if (player.hitPoints > 0) {
            return playerTurn(player, boss);
        } else {
            log.info("Player died...");
        return null;
        }
    }


    static class Spell {
        final String name;
        final int cost;
        final int damage;
        final int heal;
        final int duration;

        public Spell(String name, int cost, int damage, int heal, int duration) {
            this.name = name;
            this.cost = cost;
            this.damage = damage;
            this.heal = heal;
            this.duration = duration;
        }
    }

    static class Player {
        int hitPoints;
        int mana;
        int manaSpent;
        int armor;
        Map<Spell, Integer> duration = new HashMap<>();

        public Player(int hitPoints, int mana) {
            this.hitPoints = hitPoints;
            this.mana = mana;
        }

        public Player(Player player) {
            this.mana = player.mana;
            this.manaSpent = player.manaSpent;
            this.hitPoints = player.hitPoints;
            this.duration = new HashMap<>(player.duration);
        }
    }

    static class Boss {
        int hitPoints;
        final int damage;

        public Boss(int hitPoints, int damage) {
            this.hitPoints = hitPoints;
            this.damage = damage;
        }

        public Boss(Boss boss) {
            this.hitPoints = boss.hitPoints;
            this.damage = boss.damage;
        }
    }
}
