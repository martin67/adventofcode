package Astar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Dijkstra {

    /*
     * This implementation of Dijkstra's algorithm updates all nodes in a graph with their shortest distance/path to a given node
     */

    private Graph graph;
    private List<Edge> edges;
    private Set<Node> unsearched;
    private Set<Node> searched;

    public Dijkstra(Graph graph) {
        this.graph = graph;
        this.edges = new ArrayList(graph.getEdges());
    }

    /*
     * Updates all nodes with shortest distance from start. printPath(end) must be called separately.
     */
    public void run(Node start) {

        unsearched = new HashSet<>();
        searched = new HashSet<>();

        Node current = start;        //Set the current node to start
        start.setDistance(0.0);        //
        unsearched.add(start);

        while (!unsearched.isEmpty()) {
            //Set current to the node with the least distance to start
            current = getClosest();
            searched.add(current);
            unsearched.remove(current);
            updateNeighborDistances(current);
        }
    }

    /*
     * @param destination the node whose shortest path to the start node we want to print
     */
    public void printPath(Node destination) {
        System.out.println("Total distance traveled: " + destination.getDistance());
        Node current = destination;
        Stack path = new Stack();
        path.push(destination);

        //Enqueue all path nodes to a stack (so we can easily print in reverse order)
        while (current.getPrevious() != null) {
            current = current.getPrevious();
            path.push(current);
        }

        //Print out the path in the correct order
        do {
            System.out.println(path.pop());
        }
        while (!path.isEmpty());
    }

    /*
     * @param curr the node whose neighbors we will update
     */
    private void updateNeighborDistances(Node curr) {
        List<Node> neighbors = getNeighbors(curr);
        Double distance = curr.getDistance();
        for (Node neighbor : neighbors) {
            //Updates distances for nodes that neighbor @param curr
            Double temp = getDistanceFrom(curr, neighbor);
            if (distance + temp < neighbor.getDistance()) {
                neighbor.setPrevious(curr);
                neighbor.setDistance(distance + temp);
                unsearched.add(neighbor);
            }
        }
    }

    /*
     * @return the unsearched node with the shortest path to the start
     */
    private Node getClosest() {
        Node closest = null;
        for (Node node : unsearched) {
            if (closest == null) {
                closest = node;
            } else {
                if (node.getDistance() < closest.getDistance()) {
                    closest = node;
                }
            }
        }
        return closest;
    }

    private Double getDistanceFrom(Node start, Node end) {
        for (Edge e : edges) {
            if (e.getStart().equals(start) && e.getEnd().equals(end)) {
                return e.getLength();
            }
        }
        return null;
    }

    private List<Node> getNeighbors(Node n) {
        List<Node> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getStart().equals(n)) {
                neighbors.add(edge.getEnd());
            }
        }
        return neighbors;
    }

}