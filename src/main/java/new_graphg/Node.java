package new_graphg;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Node> neighbours = new ArrayList<>();
    private int index;

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

    public void setIndex(int i) {
        index = i;
    }

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
