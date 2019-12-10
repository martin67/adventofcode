package aoc2019;

import java.util.List;

public class Day1TyrannyOfTheRocketEquation {
    int sumOfFuelRequirements(List<String> modules) {
        return modules.stream()
                .mapToInt(m -> fuelRequirement(Integer.parseInt(m)))
                .sum();
    }

    int fuelRequirement(int moduleMass) {
        return moduleMass / 3 - 2;
    }

    int sumOfFuelRequirementsAddedFuelFromFile(List<String> modules) {
        return modules.stream()
                .mapToInt(m -> fuelRequirementAddedFuel(Integer.parseInt(m)))
                .sum();
    }

    int fuelRequirementAddedFuel(int moduleMass) {
        int totalFuelMass = fuelRequirement(moduleMass);
        int fuelMass = totalFuelMass;
        while (fuelMass > 0) {
            fuelMass = fuelRequirement(fuelMass);
            if (fuelMass > 0) {
                totalFuelMass += fuelMass;
            }
        }
        return totalFuelMass;
    }
}
