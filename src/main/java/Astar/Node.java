package Astar;

import java.awt.Point;
import java.util.Stack;

public class Node implements Comparable<Node> {
    private String name;
    private Double distance = Double.MAX_VALUE;
    //We need Euclidian points if we are going to use A* search (Euclidian distance will serve as our heuristic)
    private Point coord;
    private Double heuristic;
    private Node previous = null;

    public Node(String name) {
        this.name = name;
    }

    public Node(String name, int x, int y) {
        this(name);
        coord = new Point(x, y);
    }

    public String toString() {
        return name;
    }

    public Double getDistance() {
        return distance;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node n) {
        previous = n;
    }

    public void setDistance(Double d) {
        distance = d;
    }

    public boolean equals(Object o) {
        return coord.equals(((Node) o).coord);
    }

    public void setHeuristic(Node destination) {
        this.heuristic = Node.distanceFrom(this, destination);
    }

    @Override
    public int compareTo(Node n) {
        return Double.compare(heuristic + distance, n.heuristic + n.distance);
    }

    /*
     * Static helper function used to print the path of a given destination node.
     */
    public static Double distanceFrom(Node n1, Node n2) {
        return Math.sqrt((n1.coord.x - n2.coord.x) * (n1.coord.x - n2.coord.x) + (n1.coord.y - n2.coord.y) * (n1.coord.y - n2.coord.y));
    }

    public static void printPath(Node destination) {
        System.out.println("Total distance traveled: " + destination.getDistance());
        Node current = destination;
        Stack<Node> path = new Stack<>();
        path.push(destination);

        //Enqueue all path nodes to a stack (so we can easily print in reverse order)
        while (current.getPrevious() != null) {
            current = current.getPrevious();
            path.push(current);
        }

        //Print out the path in the correct order
        int i = 0;
        do {
            Node n = path.pop();
            System.out.println(++i + ": " + n + " - " + n.distance);
        }
        while (!path.isEmpty());
    }

}
