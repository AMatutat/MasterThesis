package levelg;

import stuff.DesignLabel;
import stuff.LevelElement;
import stuff.Point;

import java.util.Random;

public class Room {
    private Tile[][] layout;
    private DesignLabel design;
    private Point referencePointGlobal;
    private Point referencePointLocal;

    public Room(LevelElement[][] layout, DesignLabel label, Point referencePointLocal, Point referencePointGlobal) {
        //choose random desing label
        if (label == DesignLabel.ALL) {
            this.design = DesignLabel.values()[new Random().nextInt(DesignLabel.values().length - 1)];
        } else
            this.design = label;

        this.layout = new Tile[layout.length][layout[0].length];
        this.referencePointGlobal = referencePointGlobal;
        this.referencePointLocal = referencePointLocal;
        convertLayout(layout);
    }

    private void convertLayout(LevelElement[][] toConvert) {
        float difx = referencePointGlobal.x - referencePointLocal.x;
        float dify = referencePointGlobal.y - referencePointLocal.y;

        for (int y = 0; y < toConvert.length; y++)
            for (int x = 0; x < toConvert[0].length; x++) {
                Point p = new Point(x, y);
                String texture = TileTextureFactory.findTexture(toConvert[y][x], design, toConvert, p);
                layout[y][x] = new Tile(texture, new Point(x + difx, y + dify));
            }
        //just for test reasons
        String texture = TileTextureFactory.findTexture(LevelElement.WALL, design, toConvert, referencePointLocal);
        layout[(int) referencePointLocal.y][(int) referencePointLocal.x] = new Tile(texture, new Point(referencePointLocal.x + difx, referencePointLocal.y + dify));
    }


    public Tile[][] getLayout() {
        return layout;
    }

    public DesignLabel getDesign() {
        return design;
    }
}
