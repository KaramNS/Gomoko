package src ;

import java.io.Serializable;

/**
 * Game class representing a game of Gomoku.
 * It contains the players and the game board.
 * @author ELNASORY Karam
 */

public class Game implements Serializable
{
    private final User player1 ;
    private final User player2 ;

    /**
     * @description Constructor for Game class, 
     */
    public Game ()
    {
        this.player1 = new Humain(Color.PURPLE) ;
        this.player2 = new Computer(Color.YELLOW) ;

        
    }


}
