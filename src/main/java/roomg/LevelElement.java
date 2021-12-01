package roomg;

public enum LevelElement {
    FLOOR(0),
    WALL(1),
    TRAP(2),
    EXIT(3),
    PLACEHOLDER(-1);

    private int value;

    LevelElement(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
