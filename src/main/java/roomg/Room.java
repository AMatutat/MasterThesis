package roomg;

import stuff.DesignLabel;

import java.util.List;

public class Room {

    private int [][] layout;
    private DesignLabel design;


    public Room (int[][]layout, DesignLabel label){
        this.layout=layout;
        this.design=label;
    }

    public Room (Room r){
        this.layout=r.getLayout();
        this.design=r.getDesign();
    }

    public void replace(List<Replacement> replacements){

    }

    public int[][] getLayout() {
        return layout;
    }

    public DesignLabel getDesign() {
        return design;
    }
}
