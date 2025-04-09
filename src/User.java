package src ;

import java.util.ArrayList;

/**
 * User abstract class representing a player in the game.
 * It contains the player's name and score.
 * @author ELNASORY Karam
 */
public abstract class User 
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
     * @param startingTokenCount The number of tokens the player starts with.
     */

    public User (String name, int score, Color playerColor, int startingTokenCount) 
    {
        this.name = name ;
        this.score = score ;

        this.playerColor = playerColor ;

        this.tokens = new ArrayList<Token>(startingTokenCount) ;

        for (int i = 0 ; i < startingTokenCount ; i++)
        {
            tokens.add( new Token(playerColor) ) ;
        }
    }

    private ArrayList<Token> tokens = new ArrayList<Token>() ;

    /**
     * Getter method for the player's tokens.
     * @return ArrayList of Token objects.
     */
    public ArrayList<Token> tokens ()
    {
        return tokens ;
    }

    /**
     * @description Method to check if the player has tokens left.
     * @return boolean indicating if the player has tokens left.
     */
    public boolean haveTokens ()
    {
        return tokens.size() > 0 ;
    }

    public Token token ()
    {
        if ( this.haveTokens() )
        {
            Token t = tokens.get(0) ;
            tokens.remove(0) ;

            return t ;
        }
        else
        {
            throw new IllegalStateException("No more tokens left") ;
        }
    }

    /**
     * @description Method to choose a placement on the board to play a move (place a token).
     */
    abstract Coordonates chosePlacement () ;

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
        sb.append (" \n ") ;

        return sb.toString() ;
    }

}