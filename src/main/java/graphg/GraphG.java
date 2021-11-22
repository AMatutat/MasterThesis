package graphg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andre Matutat
 */
public class GraphG {


    public List<Graph> getGraphs(int nodes, int edges) throws CantBePlanarException, IllegalArgumentException, NoSolutionException {
        if (nodes <= 1)
            throw new IllegalArgumentException("A graph must consist of at least two nodes");
        if (edges < 0)
            throw new IllegalArgumentException("Number of additional edges cannot be negative");

        // e≤3v-6 must hold
        int minimumEdges = nodes - 1 + edges;
        int leftTerm = 3 * nodes - 6;
        if (minimumEdges > leftTerm) throw new CantBePlanarException("e<=3V-6 does not hold");


        Graph tree = new Graph();
        List<Graph> trees = new ArrayList<>();
        trees.add(tree);
        trees = calculateTrees(trees, nodes - 2);
        List<Graph> solutions = calculateGraphs(trees, edges);
        if (solutions.isEmpty()) throw new NoSolutionException("No solution found"); //??
        return solutions;
    }

    private List<Graph> calculateTrees(List<Graph> trees, int nodesLeft) {
        if (nodesLeft <= 0) return trees;
        else {
            List<Graph> newTrees = new ArrayList<>();
            for (Graph t : trees)
                for (Node n : t.getNodes()) {
                    Graph newTree = t.copy();
                    if (newTree.connectNewNode(n.getIndex()))
                        newTrees.add(newTree);
                }
            return calculateTrees(newTrees, nodesLeft - 1);
        }
    }

    private List<Graph> calculateGraphs(List<Graph> graphs, int edgesLeft) {
        if (edgesLeft <= 0) return graphs;
        else {
            List<Graph> newGraphs = new ArrayList<>();
            for (Graph g : graphs)
                for (Node n1 : g.getNodes())
                    for (Node n2 : g.getNodes()) {
                        Graph newGraph = g.copy();
                        if (n1.getIndex() != n2.getIndex() && newGraph.connectNodes(n1.getIndex(), n2.getIndex())) {
                            newGraphs.add(newGraph);
                        }
                    }
            return calculateGraphs(newGraphs, edgesLeft - 1);
        }
    }
}
