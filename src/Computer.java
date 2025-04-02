package src;

/**
 * @description This class represents a computer player in the game.
 * * It extends the User class and inherits its properties and methods.
 * 
 * @author ELNASORY Karam
 * 
 */

public class Computer extends User
{   
    /**
     * @description Constructor for Computer class, 
     * @param color The Color of the player.
     */
    public Computer (Color color)
    {
        super ("Computer", 0, color) ; // IDEA : Use a default color for the computer player

    }
}
