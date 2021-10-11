package graph;

import java.util.ArrayList;


public class Node {
    private int neighbours=0;
    private String name;


    public Node(String name) {
        this.name = name;
    }

    public void removeNeighbour(){
        this.neighbours--;
    }
    public void addNeighbour() {
        this.neighbours++;
    }
    public int getNeighbours() {
        return this.neighbours;
    }

    public String getName() {
        return name;
    }
}
