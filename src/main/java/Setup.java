import graphg.CantBePlanarException;
import graphg.Graph;
import graphg.NoSolutionException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Setup extends Thread{
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
    private MyExcelWriter ex;
    private String name;
    public boolean done = false;
    public Setup(int nodes, int edges, String []labels, int wantedRuns, MyExcelWriter ex, String name){
        this.nodes=nodes;
        this.edges=edges;
        this.labels=labels;
        this.wantedRuns=wantedRuns;
        this.ex=ex;
        this.name=name;

    }

    @Override
    public void run(){
        System.out.println("Start "+name);
        Graph g = null;
        for (int runs=0; runs<wantedRuns; runs++){
            try {
                g = new Graph(nodes,edges,labels);
                int loops = g.getBreakAfter()-g.getUntilBreak();
                solutions++;
                totalLoops+=loops;
                maximalLoops=Math.max(maximalLoops,loops);
                minimalLoops=Math.min(minimalLoops,loops);
            } catch (NoSolutionException e) {
                fails++;
            } catch (CantBePlanarException e) {
                runs=wantedRuns;
                solutions=-1;
            } finally {
                try {
                    g.resetUntilBreak();
                }
              catch (NullPointerException e){

              }
            }
        }
        if (solutions>0)
            averageLoops=totalLoops/solutions;
        System.out.println(this.name+" finished search");
        this.printResult();
        System.out.println(this.name+" finished");
        done=true;
    }



    public void printResult(){
        System.out.println(this.name+" starts writing");
        ex.addEntry(nodes,edges,wantedRuns,solutions,fails,maximalLoops,maximalLoops,averageLoops);
    }
}
