package src ;

import java.io.Serializable;

/**
 * User abstract class representing a player in the game.
 * It contains the player's name, tokens color and score.
 * @author ELNASORY Karam
 */
public abstract class User implements Serializable
{
    private final String name ;

    /**
     * Getter method for the player name.
     * @return User name (String)
     */

    public String name ()
    {
        return name ;
    }
    
    int score ;
    
    /**
     * Getter methode for the player score.
     * @return int
     */
    public int score ()
    {
        return score ;
    }

    private Color playerColor ;

    /**
     * Getter method for the player color.
     * @return Color
     */
    public Color color ()
    {
        return this.playerColor ;
    }
    
    /**
     * Constructor for User class.
     * @param name The name of the player.
     * @param score The score of the player.
     * @param playerColor The color of the player.
     */

    public User (String name, int score, Color playerColor) 
    {
        this.name = name ;
        this.score = score ;

        this.playerColor = playerColor ;
    }

    /**
     * Method to check if the player has tokens left.
     * @return boolean indicating if the player has tokens left.
     */
    public boolean haveTokens ()
    {
        return this.score() > 0 ;
    }

    public Token token ()
    {
        if ( this.haveTokens() )
        {
            Token t = new Token(playerColor) ;
            this.score -- ;

            return t ;
        }
        else
        {
            throw new IllegalStateException("No more tokens left") ;
        }
    }

    /**
     * Method to choose a placement on the board to play a move (place a token).
     */
    abstract Coordonates chosePlacement (Matrix matrix) ;

    /**
     * Methode to return the object state as a string.
     * @return String representation of the object.
     */
    public String toString () 
    {
        StringBuilder sb = new StringBuilder() ;

        sb.append ("Name : ") ;
        sb.append (name) ;
        sb.append (" | ") ;

        sb.append ("Score : ") ;
        sb.append (score) ;
        sb.append (" | ") ;

        sb.append ("Color : ") ;
        sb.append (color().toString()) ;
        sb.append (" \n ") ;

        return sb.toString() ;
    }
}
