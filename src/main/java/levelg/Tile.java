package levelg;


import stuff.Point;

public class Tile {

    private String texture;
    private Point globalPosition;

    public Tile(String texture, Point p){
        this.texture=texture;
        globalPosition=p;
    }
    public String getTexture(){
        return texture;
    }
    public Point getGlobalPosition() {return globalPosition;}
}
