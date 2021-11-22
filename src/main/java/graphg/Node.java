package graphg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andre Matutat
 */
public class Node {
    private List<Node> neighbours = new ArrayList<>();
    private int index;

    /**
     * Add this node as neighbour
     *
     * @param n
     */
    public void connect(Node n) {
        neighbours.add(n);
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

    public List<Node> getNeighbours() {
        return neighbours;
    }

    /**
     * If two nodes have the same index, they are a copy of another
     * Sets the index.
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
        for (Node n : getNeighbours())
            if (getIndex() < n.getIndex())
                dot += getIndex() + "->" + n.getIndex() + "\n";
        return dot;
    }
}
