package aoc.aoc2016;

import com.google.common.collect.Sets;
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

    private final State initialState;
    private final State finalState;
    private final Graph<String, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);

    private State readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));
        var pattern = Pattern.compile("(a|, a| and a|, and a) (\\w+)(-compatible)? (generator|microchip)");

        int floor = 1;
        Set<String> newDevices = new HashSet<>();

        for (String row : inputStrings) {
            var matcher = pattern.matcher(row);
            while (matcher.find()) {
                // hydrogen generator on 3rd floor => HG3
                String material = matcher.group(2).substring(0, 1).toUpperCase();
                String type = matcher.group(4).substring(0, 1).toUpperCase();
                newDevices.add(material + type + floor);
            }
            floor++;
        }

        // save all the devices in order to a state
        StringBuilder initialState = new StringBuilder("E1");
        newDevices.stream().sorted().forEach(d -> initialState.append("-").append(d));

        return new State(initialState.toString());
    }

    public Day11RadioisotopeThermoelectricGenerators(String fileName) throws IOException {
        initialState = readData(fileName);
        finalState = createFinalState(initialState);// kommer aldrig att gå att skapa alla tillstånd i förväg. I fall 2 är det +4M stycken
        graph.addVertex(initialState.getSignature());
        //createAllStatesRecursive(initialState);
        createAllStates();
        //createAllStates2();
        System.out.printf("Initial state: %s\nFinal state:   %s\n", initialState, finalState);

        //setupGraph();
        //setupNewGraph(initialState, finalState);
    }

    @NoArgsConstructor
    static class State {
        String name;        // E1-HM1-PG3-HG2 etc.

        State(String name) {
            this.name = name;
        }

        State(int floor, State currentState, Set<String> update) {
            // save all the devices in order to a state
            StringBuilder istate = new StringBuilder("E" + floor);

            // copy old state
            name = currentState.name;

            for (String device : currentState.getAllDevices()) {
                String currentDevice = device + currentState.getFloor(device);
                if (update.contains(device)) {
                    istate.append("-").append(device).append(floor);
                } else {
                    istate.append("-").append(currentDevice);
                }
            }
            name = istate.toString();
        }

        int getElevator() {
            return Integer.parseInt(name.substring(1, 2));
        }

        List<String> getAllDevices() {
            List<String> deviceNames = new ArrayList<>();
            Pattern pattern = Pattern.compile("([A-Z][A-Z])\\d");
            Matcher matcher = pattern.matcher(name);
            while (matcher.find()) {
                deviceNames.add(matcher.group(1));
            }
            return deviceNames;
        }

        int getFloor(String device) {
            return getDeviceFloor().get(device);
        }

        Map<String, Integer> getDeviceFloor() {
            Map<String, Integer> deviceFloor = new HashMap<>();
            for (String s : name.split("-")) {
                String[] part = s.split("(?<=\\D)(?=\\d)");
                deviceFloor.put(part[0], Integer.parseInt(part[1]));
            }
            return deviceFloor;
        }

        List<String> getDeviceNamesFromFloor(int floor) {
            List<String> deviceNames = new ArrayList<>();
            Pattern pattern = Pattern.compile("(\\w\\w)" + floor);
            Matcher matcher = pattern.matcher(name);
            while (matcher.find()) {
                deviceNames.add(matcher.group(1));
            }
            return deviceNames;
        }

        boolean isValid() {
            // Check all floors for correct setup
            for (int floor = 1; floor <= NUMBER_OF_FLOORS; floor++) {
                // Floor is not isValid if it contains a microchip without a corresponding generator, unless there is no
                // generators on the floor at all
                Set<String> microchipsOnFloor = getDeviceNamesFromFloor(floor).stream().filter(n -> n.charAt(1) == 'M').collect(Collectors.toSet());
                Set<String> generatorsOnFloor = getDeviceNamesFromFloor(floor).stream().filter(n -> n.charAt(1) == 'G').collect(Collectors.toSet());

                // if there are no generators or microchips on the floor, move on to next floor
                if (generatorsOnFloor.isEmpty() || microchipsOnFloor.isEmpty()) {
                    continue;
                }

                // if all microchips have a corresponding generator, move on to the next floor
                Set<String> microchipMaterials = microchipsOnFloor.stream().map(n -> n.substring(0, 1)).collect(Collectors.toSet());
                Set<String> generatorMaterials = generatorsOnFloor.stream().map(n -> n.substring(0, 1)).collect(Collectors.toSet());

                if (generatorMaterials.containsAll(microchipMaterials)) {
                    continue;
                }
                return false;
            }
            return true;
        }

        Set<State> validStates() {
            Set<State> validStates = new HashSet<>();

            // One state equals one move of the elevator, meaning going up or down one level

            int currentFloor = getElevator();
            List<String> devicesOnCurrentFloor = getDeviceNamesFromFloor(currentFloor);
            Set<Set<String>> allCombinations = Sets.powerSet(new HashSet<>(devicesOnCurrentFloor));
            // Remove all sets that are 0 (elevator can't be empty) or bigger than 2 (max for the elevator)
            Set<Set<String>> combinations = new HashSet<>();
            for (Set<String> set : allCombinations) {
                if (!set.isEmpty() && set.size() < 3) {
                    combinations.add(set);
                }
            }

            // go up
            if (currentFloor < NUMBER_OF_FLOORS) {
                for (Set<String> elevatorContent : combinations) {
                    int newFloor = currentFloor + 1;
                    State newState = new State(newFloor, this, elevatorContent);

                    if (newState.isValid()) {
                        validStates.add(newState);
                    }
                }
            }

            // go down
            if (currentFloor > 1) {
                for (Set<String> elevatorContent : combinations) {
                    int newFloor = currentFloor - 1;
                    State newState = new State(newFloor, this, elevatorContent);

                    if (newState.isValid()) {
                        validStates.add(newState);
                    }
                }
            }

            return validStates;
        }

        String getSignature() {
            String[] deviceArray = name.split("-");
            List<String> signature = new ArrayList<>();
            String elevator = deviceArray[0];
            for (int i = 1; i < deviceArray.length; i += 2) {
                String sig = String.valueOf(deviceArray[i].charAt(2) + deviceArray[i + 1].charAt(2));
                signature.add(sig);
            }
            return elevator + signature.stream().sorted().collect(Collectors.joining());
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private void createAllStates() {
        Set<String> done = new HashSet<>();
        Queue<State> todo = new LinkedList<>();

        todo.add(initialState);

        while (!todo.isEmpty()) {
            State current = todo.poll();
            graph.addVertex(current.getSignature());

            for (State next : current.validStates()) {
                if (!done.contains(next.getSignature())) {
                    done.add(next.getSignature());
                    todo.add(next);
                    graph.addVertex(next.getSignature());
                    graph.addEdge(current.getSignature(), next.getSignature());
                }
            }
        }
    }

    private State createFinalState(State state) {
        return new State(NUMBER_OF_FLOORS, state, new HashSet<>(state.getAllDevices()));
    }

    int minimumNumberOfSteps() {
        DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths(initialState.getSignature());
        return iPaths.getPath(finalState.getSignature()).getLength();
    }

}

