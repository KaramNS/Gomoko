package src ;

/**
 * Coordonates class representing a point in a 2D space.
 * It contains the x and y coordinates.
 * @author ELNASORY Karam
 */

public class Coordonates
{
    private int x;
    private int y;

    public Coordonates(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }

    public int x() 
    {
        return x;
    }

    public int y() 
    {
        return y;
    }

    @Override
    public String toString() 
    {
        return "Coordonates{ " + x + " , "  + y +" }" ;
    }
    
}
