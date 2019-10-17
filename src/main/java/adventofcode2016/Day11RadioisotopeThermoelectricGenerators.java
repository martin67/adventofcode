package adventofcode2016;

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

        int cost() {
            return (NUMBER_OF_FLOORS - elevator) + (NUMBER_OF_FLOORS * deviceStates.size() - deviceStates.values().stream().mapToInt(Integer::intValue).sum());
        }

        boolean valid() {
            String name = shortName();

            // Check all floors for correct setup
            for (int floor = 0; floor < NUMBER_OF_FLOORS; floor++) {
                // Floor is not valid if it contains a microchip without a corresponding generator, unless there is no
                // generators on the floor at all

                int finalFloor = floor;
                Set<Device> microchipsOnFloor = deviceStates.entrySet().stream()
                        .filter(e -> e.getValue() == finalFloor)
                        .filter(e -> e.getKey().getType().equals("microchip"))
                        .map(Map.Entry::getKey).collect(Collectors.toSet());
                Set<Device> generatorsOnFloor = deviceStates.entrySet().stream()
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

                if (generatorMaterials.containsAll(microchipMaterials)) {
                    continue;
                }
                // check for a microchip without generator
//                for (String microchipMaterial : microchipMaterials) {
//                    if (!generatorMaterials.contains(microchipMaterial)) {
//                        return false;
//                    }
//                }
                return false;
            }
            return true;
        }

        Set<State> validStates() {
            Set<State> validStates = new HashSet<>();

            // One state equals one move of the elevator, meaning going up or down one level

            // devices on current floor (where the elevator is)
            Set<Device> devicesOnCurrentFloor = deviceStates.entrySet().stream()
                    .filter(e -> e.getValue() == elevator)
                    .map(Map.Entry::getKey).collect(Collectors.toSet());
            Set<Set<Device>> possibleElevatorContents = Sets.powerSet(devicesOnCurrentFloor).stream()
                    .filter(s -> s.size() < 3 && s.size() > 0).collect(Collectors.toSet());

            // go up
            if (elevator < NUMBER_OF_FLOORS - 1) {
                for (Set<Device> elevatorContent : possibleElevatorContents) {
                    int newFloor = elevator + 1;
                    State newState = new State(this);
                    newState.elevator = newFloor;

                    for (Device device : elevatorContent) {
                        newState.deviceStates.put(device, newFloor);
                    }
                    if (newState.valid()) {
                        validStates.add(newState);
                    }
                }
            }

            // go down
            if (elevator > 0) {
                for (Set<Device> elevatorContent : possibleElevatorContents) {
                    int newFloor = elevator - 1;
                    State newState = new State(this);
                    newState.elevator = newFloor;

                    for (Device device : elevatorContent) {
                        newState.deviceStates.put(device, newFloor);
                    }
                    if (newState.valid()) {
                        validStates.add(newState);
                    }
                }
            }

            return validStates;
        }

        @Override
        public String toString() {
            return shortName();
        }

        @Override
        public int hashCode() {
            return shortName().hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return shortName().equals(state.shortName());
        }
    }


    private Set<Device> allDevices = new HashSet<>();
    private Set<State> allStates = new HashSet<>();
    private State initialState;
    private State finalState;
    private Graph<String, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);


    public Day11RadioisotopeThermoelectricGenerators(String fileName) throws IOException {
        initialState = readData(fileName);
        finalState = createFinalState();
        //createAllStatesRecursive(initialState);
        //createAllStates();
        createAllStates2();
        System.out.printf("%s\n%s\n", initialState, finalState);
        setupGraph();
        //setupNewGraph(initialState, finalState);
    }

    private State readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));
        Pattern pattern = Pattern.compile("(a|, a| and a|, and a) (\\w+)(-compatible)? (generator|microchip)");

        // First floor will be number 0!
        int floor = 0;

        State initialState = new State();
        initialState.elevator = 0;

        for (String row : inputStrings) {
            Matcher matcher = pattern.matcher(row);
            while (matcher.find()) {
                Device device = new Device(matcher.group(4), matcher.group(2));
                allDevices.add(device);
                initialState.put(device, floor);
            }
            floor++;
        }
        return initialState;
    }

    private void createAllStatesRecursive(State start) {
        // does not work with large graphs...
        allStates.add(start);
        if (allStates.size() % 1000 == 0) {
            System.out.printf("allStates; %d\n", allStates.size());
        }
        for (State nextState : start.validStates()) {
            if (!allStates.contains(nextState)) {
                createAllStatesRecursive(nextState);
            }
        }
    }

    private static <T> List<List<T>> computeCombinations(List<List<T>> lists) {
        List<List<T>> currentCombinations = Arrays.asList(Arrays.asList());
        for (List<T> list : lists) {
            currentCombinations = appendElements(currentCombinations, list);
        }
        return currentCombinations;
    }

    private static <T> List<List<T>> appendElements(List<List<T>> combinations, List<T> extraElements) {
        return combinations.stream().flatMap(oldCombination
                -> extraElements.stream().map(extra -> {
            List<T> combinationWithExtra = new ArrayList<>(oldCombination);
            combinationWithExtra.add(extra);
            return combinationWithExtra;
        }))
                .collect(Collectors.toList());
    }

       private void createAllStates2() {
        List<Device> deviceList = new ArrayList<>(allDevices);
        int size = deviceList.size();
        List<String> aList = Arrays.asList("AG", "AM");
        List<String> bList = Arrays.asList("BG", "BM");
        List<String> cList = Arrays.asList("CG", "CM");
        List<List<String>> allLists = Arrays.asList(aList, bList, cList);

        List<List<String>> hej = computeCombinations(allLists);
    }

    private void createAllStates3() {
        List<Device> deviceList = new ArrayList<>(allDevices);
        int size = deviceList.size();
        List<String> aList = Arrays.asList("AG", "AM");
        List<String> bList = Arrays.asList("BG", "BM");
        List<String> cList = Arrays.asList("CG", "CM");
        List<List<String>> allLists = Arrays.asList(aList, bList, cList);

        List<List<String>> hej = computeCombinations(allLists);
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

                if (state.valid()) {
                    allStates.add(state);
                }
            }
        }
    }

    private State createFinalState() {
        State state = new State();
        state.elevator = NUMBER_OF_FLOORS - 1;
        for (Device device : allDevices) {
            state.deviceStates.put(device, NUMBER_OF_FLOORS - 1);
        }
        return state;
    }


    private void setupNewGraph(State start, State end) {
        graph.addVertex(start.shortName());
        graph.addVertex((end.shortName()));

        State current = new State(start);

        for (int i = 0; i < 10; i++) {
            // create new neighbors
            current.validStates()
                    .forEach(s -> {
                        graph.addVertex(s.shortName());
                        graph.addEdge(start.shortName(), s.shortName());
                    });
            System.out.printf("Round: %d, cost: %d\n%s\n", i, current.cost(), current.longName());


//            ALTAdmissibleHeuristic<String, DefaultEdge> heuristic = new ALTAdmissibleHeuristic<>(g, landmarks);
//            AStarShortestPath<String, DefaultEdge> shortestPath = new AStarShortestPath<>(graph, heuristic);

            current = current.validStates().stream().min(Comparator.comparing(State::cost)).get();
        }
    }

    private void setupGraph() {
        System.out.println("Setting up graph; adding vertices");
        allStates.forEach(s -> graph.addVertex(s.shortName()));

        System.out.println("Setting up graph; adding edges");
        for (State startState : allStates) {
            Set<State> endStates = startState.validStates();
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
