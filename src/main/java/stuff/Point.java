package stuff;

public class Point {

    public float x, y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copies the point.
     */
    public Point(Point p) {
        x = p.x;
        y = p.y;
    }
}
