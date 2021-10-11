package graph;

import java.util.ArrayList;

public class Graph {
    ArrayList<Node> connectedNodes = new ArrayList<>();
    ArrayList<Node> unconnectedNodes = new ArrayList<>();
    ArrayList<Node> nodesWithManyNeighbours = new ArrayList<>();
    ArrayList<Edge> edges = new ArrayList<>();
    final int MAX_NODES_H4=4;
    final int MAX_NEIGHBOURS_H4=2;


    /**
     * Erzeuge einen Graphen mit einer bestimmten anzahl an knoten
     *
     * @param nodes anzahl der knoten
     */
    public Graph(int nodes, int extraEdges) {
        for (int i = 0; i < nodes; i++) {
            unconnectedNodes.add(new Node("" + i));
        }
        connectUnconnected();
        moreConnections(extraEdges);

    }

    /**
     * Verbinde alle Knoten zu einen Graphen.
     */
    private void connectUnconnected() {
        Node n1, n2;

        //am anfang werden die ersten beiden knoten verbunden
        n1 = unconnectedNodes.get(0);
        n1.addNeighbour();
        n2 = unconnectedNodes.get(1);
        n2.addNeighbour();
        unconnectedNodes.remove(n1);
        unconnectedNodes.remove(n2);
        connectedNodes.add(n1);
        connectedNodes.add(n2);
        edges.add(new Edge(n1, n2));


        //solange es noch unverbundene knoten gibt
        while (unconnectedNodes.size() > 0) {
            // nimm einen unverbundenen knoten
            n1 = unconnectedNodes.get(0);
            //nimm einen random verbunden knoten um sicherzustellen, dass der graph immer verbunden ist
            n2 = connectedNodes.get((int) (Math.random() * connectedNodes.size()));

            if (!(n2.getNeighbours() >= MAX_NEIGHBOURS_H4 && !nodesWithManyNeighbours.contains(n2) && nodesWithManyNeighbours.size() == MAX_NODES_H4)) {
                unconnectedNodes.remove(n1);
                connectedNodes.add(n1);
                edges.add(new Edge(n1, n2));
                n1.addNeighbour();
                n2.addNeighbour();
                if (n2.getNeighbours() > MAX_NEIGHBOURS_H4 && !nodesWithManyNeighbours.contains(n2))
                    nodesWithManyNeighbours.add(n2);
            }
        }

        System.out.println("COUNTER: "+nodesWithManyNeighbours.size());
    }

    private void moreConnections(int connections) {
        Node n1, n2;


        for (int i = 0; i < connections; i++) {
            n1 = connectedNodes.get((int) (Math.random() * connectedNodes.size()));
            n2 = connectedNodes.get((int) (Math.random() * connectedNodes.size()));

            boolean connect = true;
            System.out.println("M2N start: " + nodesWithManyNeighbours.size());
            if (
                n1.equals(n2) ||
                        (nodesWithManyNeighbours.size()==MAX_NODES_H4  && (
                                (n1.getNeighbours()>=MAX_NEIGHBOURS_H4 && !nodesWithManyNeighbours.contains(n1))   ||
                                (n2.getNeighbours()>=MAX_NEIGHBOURS_H4 && !nodesWithManyNeighbours.contains(n2))
                        )) ||
                        (nodesWithManyNeighbours.size()>MAX_NODES_H4-2 &&
                                (n1.getNeighbours()>=MAX_NEIGHBOURS_H4 && !nodesWithManyNeighbours.contains(n1))   &&
                                (n2.getNeighbours()>=MAX_NEIGHBOURS_H4 && !nodesWithManyNeighbours.contains(n2))
                        )
            )
            {
                System.out.println("No connection");
                System.out.println(n1.equals(n2));
                System.out.println(nodesWithManyNeighbours.size());
                i--;
            } else {
                Edge e = new Edge(n1, n2);
                for (Edge ed : edges) {
                    if (e.getConnectionHash() == ed.getConnectionHash()) {
                        connect = false;
                    }
                }

                if (connect) {
                    edges.add(e);
                    n1.addNeighbour();
                    n2.addNeighbour();

                    if (n1.getNeighbours() > MAX_NEIGHBOURS_H4 && !nodesWithManyNeighbours.contains(n1))
                        nodesWithManyNeighbours.add(n1);
                    if (n2.getNeighbours() > MAX_NEIGHBOURS_H4 && !nodesWithManyNeighbours.contains(n2))
                        nodesWithManyNeighbours.add(n2);

                    System.out.println("M2N end: " + nodesWithManyNeighbours.size());


                }
                //no edge created
                else i--;
            }
        }

        System.out.println("COUNTER: "+nodesWithManyNeighbours.size());
    }

    /**
     * Gibt alle Kanten in Dot format aus
     *
     * @return
     */
    public String getDot() {
        System.out.println("Edges "+edges.size());

        for (Node n: connectedNodes)
            if(n.getNeighbours()>MAX_NEIGHBOURS_H4)
            System.out.println("cn: "+ n.getNeighbours() + " "+n.getName());
        for (Node n: nodesWithManyNeighbours)
            System.out.println("mn:"+n.getName());

        String dot = "";
        for (Edge e : edges)
            dot += e.toDot() + "\n";
        return dot;
    }


    public static void main(String[] args) {
        Graph c = new Graph(10,5);
        System.out.println(c.getDot());

    }


}
