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

    public Coordonates chosePlacement (Matrix matrix) 
    {
        // Randomly choose a placement on the matrix
        int x = (int) (Math.random() * matrix.getLength()) ;
        int y = (int) (Math.random() * matrix.getLength()) ;

        return new Coordonates(x, y) ;
    }
}
