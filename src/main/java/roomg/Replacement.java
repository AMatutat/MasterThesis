package roomg;

import stuff.DesignLabel;

public class Replacement {

    int[][] layout;
    DesignLabel label;
    boolean rotate;

    public Replacement(int[][] layout, boolean rotate, DesignLabel label) {
        this.layout = layout;
        this.rotate = rotate;
        this.label = label;
    }

    public int[][] getLayout() {
        return this.layout;
    }

    public DesignLabel getDesign() {
        return this.label;
    }

    public boolean getRotate() {
        return this.rotate;
    }
}
