package roomg;

import stuff.DesignLabel;

public class Replacement {

    private int[][] layout;
    private DesignLabel label;
    private boolean rotate;

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

    public boolean canRotate() {
        return this.rotate;
    }
}
