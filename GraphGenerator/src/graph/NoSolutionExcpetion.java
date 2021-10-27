package graph;

public class NoSolutionExcpetion extends Exception{
    // Parameterless Constructor
    public NoSolutionExcpetion() {}

    // Constructor that accepts a message
    public NoSolutionExcpetion(String message)
    {
        super(message);
    }
}
