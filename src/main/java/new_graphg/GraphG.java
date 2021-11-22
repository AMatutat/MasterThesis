package new_graphg;

import java.util.ArrayList;
import java.util.List;

public class GraphG {


    public GraphG(int nodes, int edges) {
        Tree tree = new Tree();
        List<Tree> trees = new ArrayList<>();
        trees.add(tree);
        trees=calculateTrees(trees,nodes-2);
        for (Tree t : trees) {
            System.out.println("------------");
            System.out.println(t.toDot());
        }
    }

    private List<Tree> calculateTrees(List<Tree> trees, int nodesLeft) {
        if (nodesLeft <= 0) return trees;
        else {
            List<Tree> newTrees = new ArrayList<>();
            for (Tree t : trees)
                for (Node n : t.getNodes()) {
                    Tree newTree = t.copy();
                    if (newTree.connectNewNode(n.getIndex()))
                        newTrees.add(newTree);
                }
            return calculateTrees(newTrees, nodesLeft - 1);
        }
    }

    private List<Tree> calculateGraphs(List<Tree> graphs, int edgesLeft) {
        if (edgesLeft <= 0) return graphs;
        else {
            List<Tree> newGraphs = new ArrayList<>();
            for (Tree g : graphs) {
                for (Node n1 : g.getNodes()) {
                    Tree newGraph = g.copy();
                    for (Node n2 : newGraph.getNodes()) {
                        //if(!n1.equals(n2) && g.connectNodes())
                    }
                }
            }


        }

        return null;
    }
}
