package graph;

public class CantBePlanarExcpetion extends Exception{
    // Parameterless Constructor
    public CantBePlanarExcpetion() {}

    // Constructor that accepts a message
    public CantBePlanarExcpetion(String message)
    {
        super(message);
    }
}
