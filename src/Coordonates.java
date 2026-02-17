package src ;

import java.io.Serializable;

/**
 * Coordonates class representing a point in a 2D space.
 * It contains the x and y coordinates.
 * @author ELNASORY Karam
 */

public class Coordonates implements Serializable
{
    /*X coordonates */
    private int x; 
    /*Y coordonates */
    private int y; 

    /**
     * Constructor to initialize the coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Coordonates(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the x coordinate.
     * @return int The x coordinate.
     */
    public int x() 
    {
        return x;
    }

    /**
     * Getter for the y coordinate.
     * @return int The y coordinate.
     */
    public int y() 
    {
        return y;
    }

    /**
     * toString method to represent the coordinates as a string.
     * @return String The string representation of the coordinates. {x , y}
     */
    @Override
    public String toString() 
    {
        return "Coordonates{ " + x + " , "  + y +" }" ;
    }
    
}
