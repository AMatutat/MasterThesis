package levelg;

import graphg.Node;
import stuff.Point;
import java.util.List;


public class Level {
    private List<Room> rooms;
    private List<Node> nodes;
    private Point start;
    private Point end;


    public Level(List<Node> nodes, List<Room> rooms) {
        this.nodes = nodes;
        this.rooms = rooms;
    }


    public List<Room> getRooms() {
        return rooms;
    }
}
