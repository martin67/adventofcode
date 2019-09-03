package Astar;

public class Edge {
    private String name;
    private double length;
    private Node start, end;

    public Edge(String name, Node start, Node end, double l) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.length = l;
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public boolean equals(Edge e) {
        return e.getName().equals(name);
    }

}