package roomg;

import stuff.DesignLabel;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private int[][] layout;
    private DesignLabel design;
    //private transient String test="NOT IN JSON";


    public Room(int[][] layout, DesignLabel label) {
        this.layout = layout;
        this.design = label;
    }

    public Room(Room r) {
        layout = new int[r.getLayout()[0].length][r.getLayout().length];
        for (int y = 0; y < layout.length; y++)
            for (int x = 0; x < layout[0].length; x++)
                layout[x][y] = r.getLayout()[x][y];
        design = r.getDesign();
    }


    /**
     * Check if a replacement fit in a specific spot on the layout
     *
     * @param r    the replacement
     * @param xCor place the left upper corner of the replacement on this x
     * @param yCor place the left upper corner of the replacement on this y
     * @return if replacement can be done
     */
    private boolean canReplaceIn(final Replacement r, int xCor, int yCor) {
        int[][] layout = getLayout();
        int[][] rlayout = r.getLayout();
        for (int y = yCor; y < yCor + rlayout.length; y++)
            for (int x = xCor; x < xCor + rlayout[0].length; x++) {
                if (rlayout[y - yCor][x - xCor] != LevelElement.PLACEHOLDER.getValue()
                        && layout[y][x] != LevelElement.PLACEHOLDER.getValue()
                )
                    return false;
            }
        return true;
    }

    /**
     * Replace a specific spot in the layout
     *
     * @param r    the replacement
     * @param xCor place the left upper corner of the replacement on this x
     * @param yCor place the left upper corner of the replacement on this y
     * @return if replacement was done
     */
    private boolean placeIn(final Replacement r, int xCor, int yCor) {
        if (!canReplaceIn(r, xCor, yCor)) return false;
        else {
            int[][] layout = getLayout();
            int[][] rlayout = r.getLayout();
            for (int y = yCor; y < yCor + rlayout.length; y++)
                for (int x = xCor; x < xCor + rlayout[0].length; x++) {
                    if (rlayout[y-yCor][x-xCor] != LevelElement.PLACEHOLDER.getValue())
                        layout[y][x] = rlayout[y - yCor][x - xCor];
                }
            return true;
        }
    }


    public void replace(final List<Replacement> replacements) {
        int layoutHeight = getLayout().length;
        int layoutWidth = getLayout()[0].length;
        //remove all replacements that are too big
        List<Replacement> replacementList = new ArrayList<>(replacements);
        for (Replacement r : replacements) {
            if (r.getLayout()[0].length <= layoutWidth && r.getLayout().length <= layoutHeight)
                replacementList.add(r);
        }

        //check if you can replace
        boolean changes;
        do {
            changes = false;
            for (Replacement r : replacementList) {
                int rHeight = r.getLayout().length;
                int rWidth = r.getLayout()[0].length;
                for (int y = 0; y < layoutHeight - rHeight; y++)
                    for (int x = 0; x < layoutWidth - rWidth; x++)
                        if (getLayout()[y][x]==LevelElement.PLACEHOLDER.getValue() && placeIn(r, x, y))
                            changes = true;
            }
        } while (changes);

        //replace all placeholder that are left with floor
        for (int y = 0; y < layoutHeight; y++)
            for (int x = 0; x < layoutWidth; x++)
                if (getLayout()[y][x] == LevelElement.PLACEHOLDER.getValue())
                    getLayout()[y][x] = LevelElement.FLOOR.getValue();
    }

    public int[][] getLayout() {
        return layout;
    }

    public DesignLabel getDesign() {
        return design;
    }

}
