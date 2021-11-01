package graphg;

import javax.swing.plaf.synth.SynthMenuBarUI;

public class Setup {
    //confifuration parameter
    final int nodes;
    final int edges;
    final String [] labels;
    final int wantedRuns;
    //counters
    private int totalLoops=0;
    private int minimalLoops=Integer.MAX_VALUE;
    private int maximalLoops=Integer.MIN_VALUE;
    private int solutions=0;
    private int fails=0;
    private double averageLoops=0;


    public Setup(int nodes, int edges, String []labels, int wantedRuns){
        this.nodes=nodes;
        this.edges=edges;
        this.labels=labels;
        this.wantedRuns=wantedRuns;

    }

    public void run() throws CantBePlanarException{
        for (int runs=0; runs<wantedRuns; runs++){
            try {
                Graph g = new Graph(nodes,edges,labels);
                int loops = Graph.breakAfter-Graph.untilBreak;
                solutions++;
                totalLoops+=loops;
                maximalLoops=Math.max(maximalLoops,loops);
                minimalLoops=Math.min(minimalLoops,loops);
            } catch (NoSolutionException e) {
                fails++;
            }
            finally {
                Graph.untilBreak=Graph.breakAfter;
            }
        }
        if (solutions>0)
            averageLoops=totalLoops/solutions;
    }



    public void printResult(String path){
        System.out.println("Nodes: "+nodes);
        System.out.println("Edges: "+edges);
        System.out.println("Wanted runs: "+wantedRuns);
        System.out.println("Solutions: "+solutions);
        System.out.println("Fails: "+fails);
        System.out.println("Minimal loops: "+minimalLoops);
        System.out.println("Maximal loops: "+maximalLoops);
        System.out.println("Average loops: "+averageLoops);
    }


}
