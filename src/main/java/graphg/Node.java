package graphg;

import java.util.ArrayList;
import java.util.List;

/** @author Andre Matutat */
public class Node {
    private List<Integer> neighbours = new ArrayList<>();
    private int index;

    /**
     * Add this node as neighbour
     *
     * @param n
     */
    public void connect(Node n) {
        neighbours.add(n.index);
    }

    public boolean notConnectedWith(Node n) {
        if (!neighbours.contains(n.getIndex())) return true;
        else return false;
    }

    /**
     * only copys index not the neighbour list
     *
     * @return
     */
    public Node copy() {
        Node copy = new Node();
        copy.setIndex(getIndex());
        return copy;
    }

    public List<Integer> getNeighbours() {
        return neighbours;
    }

    /**
     * If two nodes have the same index, they are a copy of another Sets the index.
     *
     * @param i
     */
    public void setIndex(int i) {
        index = i;
    }

    /**
     * If two nodes have the same index, they are a copy of another
     *
     * @return
     */
    public int getIndex() {
        return index;
    }

    public String toDot() {
        String dot = "";
        for (Integer n : getNeighbours()) if (getIndex() < n) dot += getIndex() + "->" + n + "\n";
        return dot;
    }
}