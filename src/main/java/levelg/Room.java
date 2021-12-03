package levelg;

import stuff.DesignLabel;

public class Room {
    private int[][] layout;
    private DesignLabel design;
    private int index;

    public Room(int[][] layout, DesignLabel label) {
        this.layout = layout;
        this.design = label;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
