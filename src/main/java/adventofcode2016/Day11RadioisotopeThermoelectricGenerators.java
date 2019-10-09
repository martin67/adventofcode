package adventofcode2016;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import jdk.internal.dynalink.beans.CallerSensitiveDetector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.SimpleGraph;

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
    @EqualsAndHashCode
    static class State {
        Map<Device, Integer> deviceStates = new HashMap<>();

        State(State state) {
            this.deviceStates = new HashMap<>(state.getDeviceStates());
        }

        void put(Device device, Integer floor) {
            deviceStates.put(device, floor);
        }

        @Override
        public String toString() {
            List<Device> sortedDeviceList = deviceStates.keySet().stream()
                    .sorted(Comparator.comparing(Device::toString))
                    .collect(Collectors.toList());
            StringBuilder output = new StringBuilder();
            for (int i = NUMBER_OF_FLOORS; i > 0; i--) {
                output.append(String.format("F%d ", i));
                for (Device device : sortedDeviceList) {
                    if (deviceStates.get(device) == i-1) {
                        output.append(" ").append(device);
                    } else {
                        output.append(" . ");
                    }
                }
                output.append("\n");
            }
            return output.toString();
        }
    }

    private Set<Device> allDevices = new HashSet<>();
    private Set<State> allStates = new HashSet<>();
    private State initialState;
    private State finalState;
    private Graph<State, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);

    public Day11RadioisotopeThermoelectricGenerators(String fileName) throws IOException {
        State tempInitialState = readData(fileName);
        createAllStates();
        initialState = getStateFromAllStates(tempInitialState);
        finalState = getFinalState();
        System.out.printf("%s\n%s\n", initialState, finalState);
        setupGraph();
    }

    private State readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));
        Pattern pattern = Pattern.compile("(a|, a| and a|, and a) (\\w+)(-compatible)? (generator|microchip)");

        // First floor will be number 0!
        int floor = 0;

        State tempInitialState = new State();

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

    private void createAllStates() {
        Set<List<Integer>> c3 = Sets.cartesianProduct(ImmutableSet.of(1, 2, 3, 4), ImmutableSet.of(1, 2, 3, 4), ImmutableSet.of(1, 2, 3, 4), ImmutableSet.of(1, 2, 3, 4));

        int size = 4;
        List<Device> deviceList = new ArrayList<>(allDevices);

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
            State state = new State();
            for (int i = 0; i < integerList.size(); i++) {
                state.put(deviceList.get(i), integerList.get(i));
            }
//            if (validState(state)) {
            allStates.add(state);
//            }
        }

        int sssize = c3.size();
    }

    private State getFinalState() {
        // create a temporary final state where all devices are at the top floor
        State tempState = new State();
        for (Device device : allDevices) {
            tempState.deviceStates.put(device, NUMBER_OF_FLOORS - 1);
        }
        return getStateFromAllStates(tempState);
    }

    private Set<State> validStates(State start) {
        Set<State> validStates = new HashSet<>();

        // Go from floor to floor and pick all possible combinations at that floor to put in the elevator
        for (int startFloor = 0; startFloor < NUMBER_OF_FLOORS; startFloor++) {
            int finalStartFloor = startFloor;
            Set<Device> devicesOnCurrentFloor = start.deviceStates.entrySet().stream()
                    .filter(e -> e.getValue() == finalStartFloor)
                    .map(Map.Entry::getKey).collect(Collectors.toSet());
            Set<Set<Device>> possibleElevatorContents = Sets.powerSet(devicesOnCurrentFloor);

            // Now move try all these combinations to all floors to get valid states
            for (int endFloor = 0; endFloor < NUMBER_OF_FLOORS; endFloor++) {

                for (Set<Device> elevatorContent : possibleElevatorContents) {
                    State newState = new State(start);
                    for (Device device : elevatorContent) {
                        newState.deviceStates.put(device, endFloor);
                    }
                    // Find same state in allStates
                    State s2 = getStateFromAllStates(newState);

                    if (validState(s2)) {
                        validStates.add(s2);
                    }
                }
            }
        }
        return validStates;
    }

    private boolean validState(State state) {

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
                break;
            }

            // if all microchips have a corresponding generator, move on to the next floor
            Set<String> microchipMaterials = microchipsOnFloor.stream().map(Device::getMaterial).collect(Collectors.toSet());
            Set<String> generatorMaterials = generatorsOnFloor.stream().map(Device::getMaterial).collect(Collectors.toSet());

            if (microchipMaterials.containsAll(generatorMaterials)) {
                break;
            }

            return false;
        }
        return true;
    }

    private State getStateFromAllStates(State state) {
        for (State s : allStates) {
            // Compare all individual device states
            if (s.deviceStates.equals(state.deviceStates)) {
                return s;
            }
        }
        return null;
    }

    private void setupGraph() {
        System.out.println("Setting up graph; adding vertices");
        allStates.forEach(s -> graph.addVertex(s));

        System.out.println("Setting up graph; adding edges");
        for (State startState : allStates) {
            Set<State> endStates = validStates(startState);
            for (State endState : endStates) {
                graph.addEdge(startState, endState);
            }
        }

        System.out.println("Number of vertexes: " + graph.vertexSet().size());
        System.out.println("Number of edges: " + graph.edgeSet().size());
    }

    int minimumNumberOfSteps() {

        System.out.println("Shortest path from i to c:");
        DijkstraShortestPath<State, DefaultEdge> dijkstraAlg =
                new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<State, DefaultEdge> iPaths = dijkstraAlg.getPaths(initialState);
        System.out.println(iPaths.getPath(finalState) + "\n");

        return iPaths.getPath(finalState).getLength();
    }
}
