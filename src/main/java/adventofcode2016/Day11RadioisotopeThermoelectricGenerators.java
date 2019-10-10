package adventofcode2016;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day11RadioisotopeThermoelectricGenerators {

    private static final int NUMBER_OF_FLOORS = 4;

    @Data
    @AllArgsConstructor
    static class Device {
        String type;
        String material;

        @Override
        public String toString() {
            return material.substring(0, 1).toUpperCase() + type.substring(0, 1).toUpperCase();
        }
    }

    @Data
    @NoArgsConstructor
    static class State {
        int elevator;
        Map<Device, Integer> deviceStates = new HashMap<>();

        State(State state) {
            this.elevator = state.elevator;
            this.deviceStates = new HashMap<>(state.getDeviceStates());
        }

        void put(Device device, Integer floor) {
            deviceStates.put(device, floor);
        }

        String shortName() {
            List<Device> sortedDeviceList = deviceStates.keySet().stream()
                    .sorted(Comparator.comparing(Device::toString))
                    .collect(Collectors.toList());
            StringBuilder output = new StringBuilder();
            output.append(String.format("E%d", elevator + 1));
            for (Device device : sortedDeviceList) {
                output.append(String.format("-%s%d", device, deviceStates.get(device) + 1));
            }
            return output.toString();
        }

        String longName() {
            List<Device> sortedDeviceList = deviceStates.keySet().stream()
                    .sorted(Comparator.comparing(Device::toString))
                    .collect(Collectors.toList());
            StringBuilder output = new StringBuilder();
            for (int i = NUMBER_OF_FLOORS; i > 0; i--) {
                output.append(String.format("F%d ", i));
                if (elevator == i - 1) {
                    output.append(" E ");
                } else {
                    output.append(" . ");
                }
                for (Device device : sortedDeviceList) {
                    if (deviceStates.get(device) == i - 1) {
                        output.append(" ").append(device);
                    } else {
                        output.append(" . ");
                    }
                }
                output.append("\n");
            }
            return output.toString();
        }

        @Override
        public String toString() {
            return longName();
        }

        @Override
        public int hashCode() {
            return shortName().hashCode();
        }
    }


    private Set<Device> allDevices = new HashSet<>();
    private Set<State> allStates = new HashSet<>();
    private State initialState;
    private State finalState;
    private Graph<String, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);


    public Day11RadioisotopeThermoelectricGenerators(String fileName) throws IOException {
        initialState = readData(fileName);
        finalState = getFinalState();
        createAllStates(initialState);
        System.out.printf("%s\n%s\n", initialState, finalState);
        setupGraph();
    }

    private State readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));
        Pattern pattern = Pattern.compile("(a|, a| and a|, and a) (\\w+)(-compatible)? (generator|microchip)");

        // First floor will be number 0!
        int floor = 0;

        State tempInitialState = new State();
        tempInitialState.elevator = 0;

        for (String row : inputStrings) {
            Matcher matcher = pattern.matcher(row);
            while (matcher.find()) {
                Device device = new Device(matcher.group(4), matcher.group(2));
                allDevices.add(device);
                tempInitialState.put(device, floor);
            }
            floor++;
        }
        return tempInitialState;
    }

    private void createAllStates(State start) {

        if (!allStates.contains(start)) {
            allStates.add(start);
        }
        for (State nextState : validStates(start)) {
            if (!allStates.contains(nextState)) {
                createAllStates(nextState);
            }
        }
    }

    private void createAllStates() {

        List<Device> deviceList = new ArrayList<>(allDevices);
        int size = deviceList.size();

        List<Set<Integer>> intlist = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            Set<Integer> set = new HashSet<>();
            for (int j = 0; j < size; j++) {
                set.add(j);
            }
            intlist.add(set);
        }
        for (List<Integer> integerList : Sets.cartesianProduct(intlist)) {
            for (int floor = 0; floor < NUMBER_OF_FLOORS; floor++) {
                State state = new State();
                state.elevator = floor;
                for (int i = 0; i < integerList.size(); i++) {
                    state.put(deviceList.get(i), integerList.get(i));
                }

                if (validState(state)) {
                    allStates.add(state);
                }
            }
        }
    }

    private State getFinalState() {
        State state = new State();
        state.elevator = NUMBER_OF_FLOORS - 1;
        for (Device device : allDevices) {
            state.deviceStates.put(device, NUMBER_OF_FLOORS - 1);
        }
        return state;
    }

    private Set<State> validStates(State start) {
        Set<State> validStates = new HashSet<>();

        // One state equals one move of the elevator, meaning going up or down one level

        // devices on current floor (where the elevator is)
        Set<Device> devicesOnCurrentFloor = start.deviceStates.entrySet().stream()
                .filter(e -> e.getValue() == start.elevator)
                .map(Map.Entry::getKey).collect(Collectors.toSet());
        Set<Set<Device>> possibleElevatorContents = Sets.powerSet(devicesOnCurrentFloor).stream().filter(s -> s.size() < 3).collect(Collectors.toSet());

        // go up
        if (start.elevator < NUMBER_OF_FLOORS - 1) {
            for (Set<Device> elevatorContent : possibleElevatorContents) {
                int newFloor = start.elevator + 1;
                State newState = new State(start);
                newState.elevator = newFloor;

                for (Device device : elevatorContent) {
                    newState.deviceStates.put(device, newFloor);
                }
                if (validState(newState)) {
                    validStates.add(newState);
                }
            }
        }

        // go down
        if (start.elevator > 0) {
            for (Set<Device> elevatorContent : possibleElevatorContents) {
                int newFloor = start.elevator - 1;
                State newState = new State(start);
                newState.elevator = newFloor;

                for (Device device : elevatorContent) {
                    newState.deviceStates.put(device, newFloor);
                }
                if (validState(newState)) {
                    validStates.add(newState);
                }
            }
        }

        return validStates;
    }

    private boolean validState(State state) {

        String name = state.shortName();

        // Check all floors for correct setup
        for (int floor = 0; floor < NUMBER_OF_FLOORS; floor++) {
            // Floor is not valid if it contains a microchip without a corresponding generator, unless there is no
            // generators on the floor at all

            int finalFloor = floor;
            Set<Device> microchipsOnFloor = state.deviceStates.entrySet().stream()
                    .filter(e -> e.getValue() == finalFloor)
                    .filter(e -> e.getKey().getType().equals("microchip"))
                    .map(Map.Entry::getKey).collect(Collectors.toSet());
            Set<Device> generatorsOnFloor = state.deviceStates.entrySet().stream()
                    .filter(e -> e.getValue() == finalFloor)
                    .filter(e -> e.getKey().getType().equals("generator"))
                    .map(Map.Entry::getKey).collect(Collectors.toSet());

            // if there are no generators or microchips on the floor, move on to next floor
            if (generatorsOnFloor.isEmpty() || microchipsOnFloor.isEmpty()) {
                continue;
            }

            // if all microchips have a corresponding generator, move on to the next floor
            Set<String> microchipMaterials = microchipsOnFloor.stream().map(Device::getMaterial).collect(Collectors.toSet());
            Set<String> generatorMaterials = generatorsOnFloor.stream().map(Device::getMaterial).collect(Collectors.toSet());

            // check for a microchip without generator
            for (String microchipMaterial : microchipMaterials) {
                if (!generatorMaterials.contains(microchipMaterial)) {
                    return false;
                }
            }
        }
        return true;
    }


    private void setupGraph() {
        System.out.println("Setting up graph; adding vertices");
        allStates.forEach(s -> graph.addVertex(s.shortName()));

        System.out.println("Setting up graph; adding edges");
        for (State startState : allStates) {
            Set<State> endStates = validStates(startState);
            for (State endState : endStates) {
                graph.addEdge(startState.shortName(), endState.shortName());
            }
        }

        System.out.println("Number of vertexes: " + graph.vertexSet().size());
        System.out.println("Number of edges: " + graph.edgeSet().size());
    }

    int minimumNumberOfSteps() {

        System.out.println("Shortest path from i to c:");
        DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths(initialState.shortName());
        System.out.println(iPaths.getPath(finalState.shortName()) + "\n");

        for (String node : iPaths.getPath(finalState.shortName()).getVertexList()) {
            System.out.printf("%s\n\n", node);
            System.out.printf("%s\n\n", allStates.stream().filter(s -> s.shortName().equals(node)).findFirst().get().longName());

        }
        return iPaths.getPath(finalState.shortName()).getLength();
    }
}
