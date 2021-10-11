package graph;

public class Edge {

    private Node a;
    private Node b;
    private int hash;

    public Edge (Node a, Node b) {
        this.a=a;
        this.b=b;
        hash= a.hashCode()+b.hashCode();
    }

    public int getConnectionHash(){
        return hash;
    }

    public String toDot(){
        return a.getName()+"->"+b.getName();
    }
}
