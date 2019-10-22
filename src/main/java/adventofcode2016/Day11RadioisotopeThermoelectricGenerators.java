package adventofcode2016;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
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

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class Day11RadioisotopeThermoelectricGenerators {

    private static final int NUMBER_OF_FLOORS = 4;

    @Data
    @NoArgsConstructor
    static class State {
        String name;        // E1-HM1-PG3-HG2 etc.

        State(State state) {
            this.name = state.name;
        }

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

        Map<Integer, String> getFloorDevice() {
            Map<Integer, String> floorDevice = new HashMap<>();
            for (String s : name.split("-")) {
                String[] part = s.split("(?<=\\D)(?=\\d)");
                floorDevice.put(Integer.parseInt(part[1]), part[0]);
            }
            return floorDevice;
        }

        String longName() {
            StringBuilder output = new StringBuilder();
            Map<String, Integer> floorMap = getDeviceFloor();

            for (int i = NUMBER_OF_FLOORS; i > 0; i--) {
                output.append(String.format("F%d ", i));
                if (floorMap.get("E") == i) {
                    output.append(" E ");
                } else {
                    output.append(" . ");
                }
                for (String device : getAllDevices()) {
                    if (floorMap.get(device) == i) {
                        output.append(" ").append(device);
                    } else {
                        output.append(" . ");
                    }
                }
                output.append("\n");
            }
            return output.toString();
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
            Set<Set<String>> combinations = Sets.powerSet(new HashSet<>(devicesOnCurrentFloor));

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
                String sig = deviceArray[i].substring(2, 3) + deviceArray[i + 1].substring(2, 3);
                signature.add(sig);
            }
            return elevator + signature.stream().sorted().collect(Collectors.joining());
        }

        Set<String> validSignatures() {
            return validStates().stream().map(State::getSignature).collect(Collectors.toSet());
        }

        @Override
        public String toString() {
            return name;
        }
    }


    //private Set<String> allDevices = new HashSet<>();
    private Set<State> allStates = new HashSet<>();
    private Set<String> allSignatures = new HashSet<>();
    private State initialState;
    private State finalState;
    private Graph<String, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);


    public Day11RadioisotopeThermoelectricGenerators(String fileName) throws IOException {
        initialState = readData(fileName);
        finalState = createFinalState(initialState);
        // kommer aldrig att gå att skapa alla tillstånd i förväg. I fall 2 är det +4M stycken
        graph.addVertex(initialState.getSignature());
        //createAllStatesRecursive(initialState);
        createAllStates();
        //createAllStates2();
        System.out.printf("Initial state: %s\nFinal state:   %s\n", initialState, finalState);

        //setupGraph();
        //setupNewGraph(initialState, finalState);
    }

    private State readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));
        Pattern pattern = Pattern.compile("(a|, a| and a|, and a) (\\w+)(-compatible)? (generator|microchip)");

        int floor = 1;
        Set<String> newDevices = new HashSet<>();
        Set<String> newDeviceNames = new HashSet<>();

        for (String row : inputStrings) {
            Matcher matcher = pattern.matcher(row);
            while (matcher.find()) {
                // hydrogen generator on 3rd floor => HG3
                String material = matcher.group(2).substring(0, 1).toUpperCase();
                String type = matcher.group(4).substring(0, 1).toUpperCase();
                newDevices.add(material + type + floor);
                newDeviceNames.add(material + type);
            }
            floor++;
        }

        // save all the devices in order to a state
        StringBuilder istate = new StringBuilder("E1");
        newDevices.stream().sorted().forEach(d -> istate.append("-").append(d));
        State initialState = new State(istate.toString());

        // Some tests
        List<String> devicesOnFloor1 = initialState.getDeviceNamesFromFloor(1);
        boolean ok = initialState.isValid();
        Set<State> validStates = initialState.validStates();
        String t2 = initialState.getSignature();
        Set<String> validSignatures = initialState.validSignatures();
        return initialState;
    }

    private void createAllStatesRecursive(State start) {
        // does not work with large graphs...
        allStates.add(start);
        allSignatures.add(start.getSignature());

        if (allStates.size() % 1000 == 0) {
            System.out.printf("allStates; %d\n", allStates.size());
        }
        for (State nextState : start.validStates()) {
            if (!allSignatures.contains(nextState.getSignature())) {
                allSignatures.add(nextState.getSignature());
                graph.addVertex(nextState.getSignature());
                graph.addEdge(start.getSignature(), nextState.getSignature());
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
                .collect(toList());
    }


    private void createAllStates() {
        Set<String> done = new HashSet<>();
        Queue<State> todo = new LinkedList<>();

        todo.add(initialState);
        //graph.addVertex(initialState.getSignature());


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


    private void setupNewGraph(State start, State end) {
        graph.addVertex(start.toString());
        graph.addVertex(end.toString());

        State current = new State(start);

        for (int i = 0; i < 10; i++) {
            // create new neighbors
            current.validStates()
                    .forEach(s -> {
                        graph.addVertex(s.toString());
                        graph.addEdge(start.toString(), s.toString());
                    });
//            System.out.printf("Round: %d, cost: %d\n%s\n", i, current.cost(), current.longName());


            MyHeuristic heuristic = new MyHeuristic();
            AStarShortestPath shortestPath = new AStarShortestPath<>(graph, heuristic);

            GraphPath path = shortestPath.getPath(start.toString(), end.toString());

//            current = current.validStates().stream().min(Comparator.comparing(State::cost)).get();
        }
    }

    private void setupGraph() {
        System.out.println("Setting up graph; adding vertices");
        allStates.forEach(s -> graph.addVertex(s.toString()));

        System.out.println("Setting up graph; adding edges");
        for (State startState : allStates) {
            Set<State> endStates = startState.validStates();
            for (State endState : endStates) {
                graph.addEdge(startState.toString(), endState.toString());
            }
        }

        System.out.println("Number of vertexes: " + graph.vertexSet().size());
        System.out.println("Number of edges: " + graph.edgeSet().size());
    }

    int minimumNumberOfSteps() {

        System.out.println("Shortest path from i to c:");
        DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths(initialState.getSignature());
        System.out.println(iPaths.getPath(finalState.getSignature()) + "\n");

        for (String node : iPaths.getPath(finalState.getSignature()).getVertexList()) {
            System.out.printf("%s\n\n", node);
//            System.out.printf("%s\n\n", allStates.stream().filter(s -> s.toString().equals(node)).findFirst().get().longName());

        }
        return iPaths.getPath(finalState.getSignature()).getLength();
    }

    static class MyHeuristic implements AStarAdmissibleHeuristic {

        @Override
        public double getCostEstimate(Object sourceVertex, Object targetVertex) {
            State src = (State) sourceVertex;
            State dst = (State) targetVertex;
//            return src.cost() - dst.cost();
            return 0;
        }

        @Override
        public boolean isConsistent(Graph graph) {
            return false;
        }
    }
}

