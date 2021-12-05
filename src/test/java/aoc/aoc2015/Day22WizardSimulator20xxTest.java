package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2015: Day 22: Wizard Simulator 20XX")
class Day22WizardSimulator20xxTest {

    @Test
    void problem1() {
        Day22WizardSimulator20xx day22WizardSimulator20xx = new Day22WizardSimulator20xx();
        assertEquals(111, day22WizardSimulator20xx.problem1());
    }

    @Test
    void problem2() {
        Day22WizardSimulator20xx day22WizardSimulator20xx = new Day22WizardSimulator20xx();
        assertEquals(188, day22WizardSimulator20xx.problem2());
    }

}