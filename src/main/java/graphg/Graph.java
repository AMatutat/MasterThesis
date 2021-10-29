package graphg;
import java.util.ArrayList;

/**
 * creates a planar graph according to the method presented in the thesis.
 * according to the method presented,
 * every graph is planar that has a maximum of 4 nodes with more than 2 neighbors.
 *
 * @author André Matutat
 */
public final class Graph {

    // edges are basically the graph
    private ArrayList<Edge> edges = new ArrayList<>();
    // all nodes that have more than MAX_NEIGHBOURS neighbors
    private ArrayList<Node> nodesWithManyNeighbours = new ArrayList<>();
    // maximum number of nodes allowed to have more than MAX_NEIGHBOURS neighbors
    private final int MAX_NODES =4;
    // maximum number of neighbors before a node is included in the nodesWithManyNeighbours list
    private final int MAX_NEIGHBOURS =2;
    //enable for some debug outputs
    public boolean debug=false;

    public static final int breakAfter =10000000;
    public static int untilBreak=breakAfter;

    /**
     * Creates a graph with the desired number of nodes.
     * Initially, each node is connected to a random node.
     * Additionally further random edges can be drawn in.
     *
     * @param nodeNumber Number of desired nodes in the graph
     * @param extraEdges number of additional edges requested
     * @param labels List with names for the nodes
     * @throws IllegalArgumentException
     */
    public Graph(final int nodeNumber, final int extraEdges, final String [] labels) throws CantBePlanarException, IllegalArgumentException, NoSolutionException {
        if (nodeNumber<=1) throw new IllegalArgumentException("A graph must consist of at least two nodes");
        if (extraEdges<0) throw new IllegalArgumentException("Number of additional edges cannot be negative");
        if (labels.length<nodeNumber) throw new IllegalArgumentException("The list of names for the nodes must not be smaller than the number of nodes");


        //e≤3v-6 must hold
        int minimumEdges = nodeNumber-1+extraEdges;
        int leftTerm= 3*nodeNumber-6;
        if (minimumEdges>leftTerm) throw new CantBePlanarException("e<=3V-6 does not hold");

        ArrayList<Node> nodes = new ArrayList<>();

        //Create nodes with names and add them to the list
        for (int i = 0; i < nodeNumber; i++) {
            nodes.add(new Node(labels[i]));
        }
        connectUnconnectedNodes(nodes);
        if(extraEdges>0) addMoreEdges(extraEdges, nodes);

    }

    /**
     * Connects all nodes in the list
     * @param nodes Contains only unconnected nodes
     */
    private void connectUnconnectedNodes(final ArrayList <Node> nodes) {
        if(debug) System.out.println("start connectUnconnectedNodes");
        //all nodes that have no neighbor
        ArrayList<Node> unconnectedNodes = new ArrayList<>(nodes);
        //all nodes that have at least one neighbor
        ArrayList<Node> connectedNodes = new ArrayList<>();

        Node node1, node2;

        //Connect the first two nodes
        node1 = unconnectedNodes.get(0);
        node1.addNeighbour();
        node2 = unconnectedNodes.get(1);
        node2.addNeighbour();
        unconnectedNodes.remove(node1);
        unconnectedNodes.remove(node2);
        connectedNodes.add(node1);
        connectedNodes.add(node2);
        edges.add(new Edge(node1, node2));

        //as long as there are still unconnected nodes
        while (unconnectedNodes.size() > 0) {
            node1 = unconnectedNodes.get(0);
            //take a randomly connected node to make sure that the new node is connected to the graph
            node2 = connectedNodes.get((int) (Math.random() * connectedNodes.size()));

            if (canConnect(node2)) {
                //connect nodes
                unconnectedNodes.remove(node1);
                connectedNodes.add(node1);
                edges.add(new Edge(node1, node2));
                node1.addNeighbour();
                node2.addNeighbour();

                //if necessary, add node to the list with many neighbors
                if (node2.getNeighbourCount() > MAX_NEIGHBOURS && !nodesWithManyNeighbours.contains(node2))
                    nodesWithManyNeighbours.add(node2);
            }
        }
        if(debug) System.out.println("finisch connectUnconnectedNodes");
    }



    private void addMoreEdges(final int connections, final ArrayList <Node> nodes) throws NoSolutionException {
        if(debug) System.out.println("Start addMoreEdges");

        Node node1, node2;

        for (int i = 0; i < connections; i++) {
            untilBreak--;
            if (untilBreak==0) throw new NoSolutionException("Cant find solution!");
            if(debug) System.out.println(i+" edges created");
            //Pick two random nodes
            node1 = nodes.get((int) (Math.random() * nodes.size()));
            node2 = nodes.get((int) (Math.random() * nodes.size()));

            //helper
            boolean connect = true;

            if (!node1.equals(node2) && canConnect(node1,node2)) {


                Edge edge = new Edge(node1, node2);
                for (Edge ed : edges) {
                    if (edge.getConnectionHash() == ed.getConnectionHash()) {
                        connect = false;
                    }
                }

                if (connect) {
                    edges.add(edge);
                    node1.addNeighbour();
                    node2.addNeighbour();

                    //if necessary, add node(s) to the list with many neighbors
                    if (node1.getNeighbourCount() > MAX_NEIGHBOURS && !nodesWithManyNeighbours.contains(node1))
                        nodesWithManyNeighbours.add(node1);
                    if (node2.getNeighbourCount() > MAX_NEIGHBOURS && !nodesWithManyNeighbours.contains(node2))
                        nodesWithManyNeighbours.add(node2);
                }
                //no edge created
                else i--;
            }
            //no edge created
            else i--;
        }

    }

    /**
     * if not: the selected node would have too many neighbors with the new connection,
     * but is not yet in the list but the list is full
     *
     * @param n
     * @return
     */
    private boolean canConnect(Node n){
        return  (!(n.getNeighbourCount() >= MAX_NEIGHBOURS && !nodesWithManyNeighbours.contains(n)
                && nodesWithManyNeighbours.size() <= MAX_NODES));
    }

    /**
     * can both nodes be connect without having to many nodes with many neighbours
     * @param n1
     * @param n2
     * @return
     */
    private boolean canConnect(Node n1, Node n2){

        boolean addN1= (n1.getNeighbourCount() >= MAX_NEIGHBOURS && !nodesWithManyNeighbours.contains(n1));
        boolean addN2= (n2.getNeighbourCount() >= MAX_NEIGHBOURS && !nodesWithManyNeighbours.contains(n2));

        return (nodesWithManyNeighbours.size()+ (addN1? 1:0)   + (addN2? 1:0) <MAX_NODES);

    }

    public String getDot() {
        System.out.println(nodesWithManyNeighbours.size());

          String dot = "digraph G {\nedge [dir=none]\n";
        for (Edge e : edges)
            dot += e.toDot() + "\n";
        return dot+"}";
    }
}
