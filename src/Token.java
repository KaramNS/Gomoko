package src;

import java.io.Serializable;

/**
 * The class represents a token that you can put in the grid, 
 * it is the Atom of the game
 * @author Largeron Jean-Baptiste + Elnasory Karam
 */
public class Token implements Serializable
{
    private  final Color color;


    /*################# Constructors ################ */

    
    /**
     * Constructor for the token, set his color with a specified color
     * @param newColor The specified color for the new token
     */
    Token(Color newColor){
        this.color = newColor;
    }



    /*############### Getters/Setters ############## */

    /**
     * Gets the color of the token
     * @return the color of the token
     */
    public Color getColor(){
        return this.color;
    }


    /*################# toString() ################ */

    @Override
    public String toString() {
        return ""+this.getColor();
    }
}
