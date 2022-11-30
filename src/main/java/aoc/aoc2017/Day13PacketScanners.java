package aoc.aoc2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Day13PacketScanners {
    // <Layer, Depth>
    final Map<Integer, Integer> firewall = new HashMap<>();
    // <Layer, scanner pos>
    final Map<Integer, Scanner> scanners = new HashMap<>();
    final int maxLayer;

    public Day13PacketScanners(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s = line.split(": ");
            firewall.put(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
            scanners.put(Integer.parseInt(s[0]), new Scanner(0, -1));
        }
        maxLayer = firewall.keySet().stream().mapToInt(l -> l).max().orElseThrow(NoSuchElementException::new);
    }

    int tripSeverity() {
        int position = -1;
        int severity = 0;

        for (int tick = 0; tick < maxLayer + 1; tick++) {
            position++;
            if (scanners.containsKey(position) && scanners.get(position).position == 0) {
                severity += position * firewall.get(position);
            }
            moveScanners();
        }
        return severity;
    }

    private void moveScanners() {
        for (int layer : firewall.keySet()) {
            if (scanners.get(layer).position == 0 || scanners.get(layer).position == firewall.get(layer) - 1) {
                scanners.get(layer).direction *= -1;
            }
            scanners.get(layer).position += scanners.get(layer).direction;
        }
    }

    private void resetScanners() {
        for (Scanner scanner : scanners.values()) {
            scanner.position = 0;
            scanner.direction = -1;
        }
    }

    // Too slow for case 2...
    int packetDelay() {
        boolean caught;
        int delay = -1;
        int maxPosition = 0;

        do {
            delay++;
            resetScanners();
            caught = false;
            for (int i = 0; i < delay; i++) {
                moveScanners();
            }

            for (int position = 0; position < maxLayer + 1; position++) {
                if (scanners.containsKey(position) && scanners.get(position).position == 0) {
                    caught = true;
                    if (position > maxPosition) {
                        maxPosition = position;
                    }
                    break;
                }
                moveScanners();
            }
        } while (caught);
        return delay;
    }

    int packetDelay2() {
        int delay = 0;
        boolean caught;

        do {
            caught = false;
            for (int layer : firewall.keySet()) {
                if ((delay + layer) % ((firewall.get(layer) - 1) * 2) == 0) {
                    caught = true;
                    break;
                }
            }
            delay++;
        } while (caught);
        return --delay;
    }

    static class Scanner {
        int position;
        int direction;

        public Scanner(int position, int direction) {
            this.position = position;
            this.direction = direction;
        }

    }

}
