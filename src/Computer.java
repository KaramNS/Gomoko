package src;

import java.util.Random;

/**
 * @description This class represents a computer player in the game.
 * * It extends the User class and inherits its properties and methods.
 * 
 * @author ELNASORY Karam - Syrine BEN HASSINE
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
        super ("Computer", 15, color) ; // IDEA : Use a default color for the computer player
    }

    /**
     * @description This method is used to choose a placement on the matrix.
     * @param matrix
     * @return
     */
    public Coordonates chosePlacement (Matrix matrix) 
    {
        // TODO : backtracking algorithm to find the best placement
        // Randomly choose a placement on the matrix
        int x = (int) (Math.random() * matrix.getLength()) ;
        int y = (int) (Math.random() * matrix.getLength()) ;

        return new Coordonates(x, y) ;
    }

    /**
     * @description This method is used to choose a placement on the matrix.
     * It randomly chooses a placement on the matrix.
     * @return Coordonates The coordinates of the placement.
     */
    @Override
    public Coordonates chosePlacement() 
    {
        Random random = new Random() ;
        Coordonates placement = new Coordonates(random.nextInt(15) , random.nextInt(15)) ;
        return placement ;
    }
}
