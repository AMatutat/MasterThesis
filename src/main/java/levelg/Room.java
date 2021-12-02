package levelg;

import stuff.DesignLabel;

public class Room {
    private int[][] layout;
    private DesignLabel design;

    public Room(int[][] layout, DesignLabel label) {
        this.layout = layout;
        this.design = label;
    }
}
