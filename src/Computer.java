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
     * Constructor for Computer class, 
     * @param color The Color of the player.
     * @description creats a computer player with a default score of 15.
     */
    public Computer (Color color)
    {
        super ("Computer", 15, color) ; // IDEA : Use a default color for the computer player
    }

    /**
     * Constructor for Computer class, 
     * @param score initial score of the player.
     * @param color The Color of the player tokens.
     * @description This constructor is meant to be used to set a custom score for the computer player.
     */
    public Computer (int score, Color color)
    {
        super ("Computer", score, color) ; // IDEA : Use a default color for the computer player
    }
    
    /**
     * This method is used to prompt the computer to choose a placement on the matrix.
     * It randomly chooses a placement on the matrix.
     * @param matrix that the computer will play on.
     * @return Coordonates The coordinates of the placement.
     * @description This method is meant to be used with a backtracking algorithm to find the best placement.
     */
    @Override
    public Coordonates chosePlacement(Matrix matrix) 
    {
        // TODO : backtracking algorithm to find the best placement

        Random random = new Random() ;
        Coordonates placement = new Coordonates(random.nextInt(15) , random.nextInt(15)) ;
        return placement ;
    }
}
