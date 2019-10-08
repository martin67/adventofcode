package adventofcode2016;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day11RadioisotopeThermoelectricGenerators {

    private final int NUMBER_OF_FLOORS = 4;

    @Data
    @AllArgsConstructor
    static class Device {
        String type;
        String material;
    }

    @Data
    static class State {
        Map<Device, Integer> state = new HashMap<>();

        void put(Device device, Integer floor) {
            state.put(device, floor);
        }
    }

    private Set<Device> devices = new HashSet<>();
    private Set<State> states = new HashSet<>();

    public Day11RadioisotopeThermoelectricGenerators(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));
        Pattern pattern = Pattern.compile("(a|, a| and a|, and a) (\\w+)(-compatible)? (generator|microchip)");
        int floor = 1;

        State initialState = new State();

        for (String row : inputStrings) {
            Matcher matcher = pattern.matcher(row);
            while (matcher.find()) {
                Device device = new Device(matcher.group(4), matcher.group(2));
                devices.add(device);
                initialState.put(device, floor);
            }
            floor++;
        }
        states.add(initialState);
    }

    private void createAllStates() {

        for (int floor = 1; floor < NUMBER_OF_FLOORS + 1; floor++) {
            for (Device device : devices) {
                State state = new State();
//                    state.put(d)

            }
        }
    }


    private Set<State> validStates(State start) {
        Set<State> validStates = new HashSet<>();

        // A state change means that we take move one or two devices from one floor to another. The conditions are also
        // 1. a microchip must be connected to it's own generator if there is a another generator on the same level
        // 2. the elevator stops on each floor, and a condition 1 must also be met

        for (int startFloor = 1; startFloor < NUMBER_OF_FLOORS + 1; startFloor++) {
            for (int endFloor = 1; endFloor < NUMBER_OF_FLOORS + 1; endFloor++) {
                if (startFloor == endFloor) break;

//                Set<Device> availableDevices = start.state.entrySet().stream()
//                        .filter(e -> e.getValue() == 1).collect(Collectors.toSet());


                        //.filter(Map.Entry<Device, Integer>::getValue() == 1);

            }
        }
        return validStates;
    }

    int minimumNumberOfSteps() {
        return 0;
    }
}
