package src ;

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
     */

    public User (String name, int score, Color playerColor) 
    {
        this.name = name ;
        this.score = score ;

        this.playerColor = playerColor ;
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