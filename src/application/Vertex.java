package application;

import java.util.*;

public class Vertex implements Comparable<Vertex> {

    private City City;
    private ArrayList<Adjacent> adj;
    private double weight;
    private Vertex prev;


    public Vertex (City City) {
        this.City = City;
        this.weight = Double.MAX_VALUE;
        this.adj = new ArrayList<Adjacent>();

    }

    public void addNeighbour(Adjacent Adjacent) {
        this.adj.add(Adjacent);
    }

    public City getCity() {
        return City;
    }

    public void setCity(City City) {
        this.City = City;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ArrayList<Adjacent> getAdj() {
        return adj;
    }

    public void setAdj(ArrayList<Adjacent> adj) {
        this.adj = adj;
    }

    public Vertex getPrev() {
        return prev;
    }

    public void setPrev(Vertex prev) {
        this.prev = prev;
    }

    @Override
    public int compareTo(Vertex o) {
        return Double.compare(this.weight,o.getWeight());
    }
}
