package adventofcode2016;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
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
        createAllStates();
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
        Set<List<Integer>> c3 = Sets.cartesianProduct(ImmutableSet.of(1, 2, 3, 4), ImmutableSet.of(1, 2, 3, 4), ImmutableSet.of(1, 2, 3, 4), ImmutableSet.of(1, 2, 3, 4));

        int size = 4;
        List<Device> deviceList = new ArrayList<>(devices);

        List<Set<Integer>> intlist = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            Set<Integer> set = new HashSet<>();
            for (int j = 0; j < size; j++) {
                set.add(j);
            }
            intlist.add(set);
        }
        Set<List<Integer>> c4 = Sets.cartesianProduct(intlist);
        for (List<Integer> integerList : c4) {

            //for(Integer i : integerList) {
            for (int i=0; i < integerList.size(); i++) {
                State state = new State();
                state.put(deviceList.get(i), integerList.get(i));
                states.add(state);
            }
        }
        // Dynamic
//        Set<Set<Device>> c2 = Sets.combinations(devices, 4);

//        Set<Set<Device>> c3 = Sets.cartesianProduct(devices, 5);

        int sssize = c3.size();
    }


    private Set<State> validStates(State start) {
        Set<State> validStates = new HashSet<>();

        // List all possible states from start
        // This would mean to go through all floors and move 1 or two devices to the other floors
        for (int startFloor = 1; startFloor < NUMBER_OF_FLOORS + 1; startFloor++) {
            for (int endFloor = 1; endFloor < NUMBER_OF_FLOORS + 1; endFloor++) {
                if (startFloor == endFloor) break;

//                Set<Device> startDevices =
                Set<Map.Entry<Device, Integer>> s = start.getState().entrySet().stream().filter(e -> e.getValue() == 2).collect(Collectors.toSet());

//                Set<Map.Entry<Device, Integer>> t = start.getState().entrySet().stream().filter(e -> e.getValue() == startFloor).collect(Collectors.toSet());

//                Set<Device> availableDevices = start.state.entrySet().stream()
//                        .filter(e -> e.getValue() == 1).collect(Collectors.toSet());


                //.filter(Map.Entry<Device, Integer>::getValue() == 1);

            }
        }
        return validStates;
    }

    boolean validState(State s) {
        // state is valid if there is no single microchip on a floor with a generator
        Set<Device> microchips = s.state.keySet().stream().filter(d -> d.getType().equals("microchip")).collect(Collectors.toSet());
        Set<Device> generators = s.state.keySet().stream().filter(d -> d.getType().equals("generator")).collect(Collectors.toSet());

        // always valid if there's no generator
        if (generators.size() == 0)
            return true;

        Set<String> generatorMaterials = generators.stream().map(Device::getMaterial).collect(Collectors.toSet());
        for (Device microchip : microchips) {
            // if there is corresponding generator, then it's valid
            if (!generatorMaterials.contains(microchip.getMaterial())) {
                return false;
            }
        }
        return true;
    }

    int minimumNumberOfSteps() {
        return 0;
    }
}
