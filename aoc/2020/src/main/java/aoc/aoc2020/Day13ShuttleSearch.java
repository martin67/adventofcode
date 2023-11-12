package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day13ShuttleSearch {
    private final List<Bus> buses = new ArrayList<>();
    private final List<Long> ids = new ArrayList<>();
    private int initialDelay;

    public Day13ShuttleSearch(List<String> inputLines) {
        initialDelay = Integer.parseInt(inputLines.get(0));
        int departureDelay = 0;

        for (String busId : inputLines.get(1).split(",")) {
            if (!busId.equals("x")) {
                int id = Integer.parseInt(busId);
                buses.add(new Bus(id, departureDelay));
            }
            departureDelay++;
        }
    }

    public Day13ShuttleSearch(String input) {
        int departureDelay = 0;
        for (String busId : input.split(",")) {
            if (!busId.equals("x")) {
                int id = Integer.parseInt(busId);
                buses.add(new Bus(id, departureDelay));
            }
            departureDelay++;
        }

        for (String id : input.split(",")) {
            ids.add(id.equals("x") ? -1 : Long.parseLong(id));
        }
    }

    int busWait() {
        for (Bus bus : buses) {
            bus.move(initialDelay);
        }
        int delay = initialDelay;
        while (true) {
            for (Bus bus : buses) {
                bus.move(1);
                if (bus.location == 0) {
                    return bus.roundTripTime * (delay - initialDelay + 1);
                }
            }
            delay++;
        }
    }

    long earliestTimestamp() {
        boolean found = false;

        long tincrement = buses.get(0).roundTripTime;
        long t = 0;
        int index = 1;

        while (!found) {
            t += tincrement;
            if ((t + buses.get(index).departureDelay) % buses.get(index).roundTripTime == 0) {
                //log.info("Found bus {} at t {}", buses.get(index).roundTripTime, t);
                tincrement *= buses.get(index).roundTripTime;
                //log.info("Setting increment to {}", tincrement);
                index++;
                if (index == buses.size()) {
                    found = true;
                }
            }
        }
        return t;
    }

    static class Bus {
        final int roundTripTime;
        final int departureDelay;
        int location = 0;

        public Bus(int roundTripTime, int departureDelay) {
            this.roundTripTime = roundTripTime;
            this.departureDelay = departureDelay;
        }

        void move(int timestamps) {
            location = (location + timestamps + roundTripTime) % roundTripTime;
        }
    }

}
