package aoc.aoc2017;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day24ElectromagneticMoat {

    final List<Component> components = new ArrayList<>();
    final Set<Bridge> bridges = new HashSet<>();

    public Day24ElectromagneticMoat(List<String> inputLines) {
        inputLines.forEach(line -> {
            String[] s = line.split("/");
            components.add(new Component(Integer.parseInt(s[0]), Integer.parseInt(s[1])));
        });
    }

    public int problem1() {
        List<Component> startComponents = components.stream()
                .filter(component -> component.portA == 0 || component.portB == 0)
                .toList();
        int maxStrength = 0;
        for (Component component : startComponents) {
            List<Component> nextComp = new ArrayList<>(components);
            nextComp.remove(component);
            int strength = component.strength(component, component.otherPort(0), 0, 0, nextComp);
            if (strength > maxStrength) {
                maxStrength = strength;
            }
        }
        return maxStrength;
    }

    public int problem2() {
        List<Component> startComponents = components.stream()
                .filter(component -> component.portA == 0 || component.portB == 0)
                .toList();
        for (Component component : startComponents) {
            List<Component> nextComp = new ArrayList<>(components);
            nextComp.remove(component);
            component.strength(component, component.otherPort(0), 0, 0, nextComp);
        }
        return bridges.stream().max(Comparator.comparing(Bridge::getLength)
                .thenComparing(Bridge::getStrength)).orElseThrow().strength;
    }

    class Component {
        final int portA;
        final int portB;

        public Component(int portA, int portB) {
            this.portA = portA;
            this.portB = portB;
        }

        boolean validPort(int port) {
            return (port == portA || port == portB);
        }

        int otherPort(int value) {
            if (value == portA) {
                return portB;
            } else {
                return portA;
            }
        }

        int strength(Component firstComponent, int startPort, int currentLength, int currentStrength, List<Component> comp) {
            //log.info("strength: {}, start: {}, comp: {}, curLen: {}, curStr: {}", this, startPort, comp, currentLength, currentStrength);
            comp.remove(this);
            List<Component> connected = comp.stream()
                    .filter(component -> component.validPort(startPort))
                    .toList();

            if (connected.isEmpty()) {
                bridges.add(new Bridge(firstComponent, this, currentStrength + portA + portB, currentLength + 1));
                return portA + portB;
            }

            int maxStrength = 0;
            for (Component component : connected) {
                List<Component> nextComp = new ArrayList<>(comp);
                nextComp.remove(component);
                int strength = component.strength(firstComponent, component.otherPort(startPort), currentLength + 1, currentStrength + portA + portB, nextComp);
                if (strength > maxStrength) {
                    maxStrength = strength;
                }
            }
            return portA + portB + maxStrength;
        }

        @Override
        public String toString() {
            return portA + "/" + portB;
        }
    }

    class Bridge {
        final Component start;
        final Component end;
        final int strength;
        final int length;

        public Bridge(Component start, Component end, int strength, int length) {
            this.start = start;
            this.end = end;
            this.strength = strength;
            this.length = length;
        }

        public int getStrength() {
            return strength;
        }

        public int getLength() {
            return length;
        }
    }
}