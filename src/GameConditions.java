package src;

import java.io.Serializable;

/**
 * GameConditions class representing the conditions of a game, and manages setting them.
 * It contains the player score, matrix size, and win condition.
 * @author ELNASORY Karam
 */

public class GameConditions implements Serializable
{
    private final int playerScore ;
    private final int MatrixSize ; // Square matrix size
    private final int winCondition ; // Number of pieces in a row to win

    /**
     * @description Constructor for GameConditions class
     * @param playerScore
     * @param MatrixSize
     * @param winCondition
     */
    public GameConditions ( int playerScore, int MatrixSize, int winCondition )
    {
        this.playerScore = playerScore ;
        this.MatrixSize = MatrixSize ;
        this.winCondition = winCondition ;
    }

    /**
     * @description Constructor for GameConditions class, default values
     * playerScore = 15, MatrixSize = 15, winCondition = 5
     */
    public GameConditions ()
    {
        this.playerScore = 15 ;
        this.MatrixSize = 15 ;
        this.winCondition = 5 ;
    }

    /**
     * @description A getter for the player score
     * @return 
     */
    public int getPlayerScore ()
    {
        return playerScore ;
    }

    /**
     * @description A getter for the matrix size
     * @return
     */
    public int getMatrixSize ()
    {
        return MatrixSize ;
    }

    /**
     * @description A getter for the win condition
     * @return
     */
    public int getWinCondition ()
    {
        return winCondition ;
    }

    /**
     * @description Validate win conditions
     * @param playerScore
     * @param MatrixSize
     * @param winCondition
     * 
     * @throws IllegalArgumentException if the win condition is greater than the matrix size, or if the player score or matrix size is negative.
     * @param playerScore
     */
    public static void validateWinConditions ( int playerScore, int MatrixSize, int winCondition ) throws IllegalArgumentException 
    {
        if ( winCondition < 0 ) 
        {
            throw new IllegalArgumentException("Win condition cannot be negative.") ;
        }
        if ( winCondition > MatrixSize ) 
        {
            throw new IllegalArgumentException("Win condition cannot be greater than matrix size.") ;
        }
        if ( playerScore < 0 ) 
        {
            throw new IllegalArgumentException("Player score cannot be negative.") ;
        }
        if ( MatrixSize < 0 ) 
        {
            throw new IllegalArgumentException( "Matrix size cannot be negative.") ;
        }
    }

    /**
     * @description 
     * @return GameConditions object with custom values entered by the user
     */
    public static GameConditions customeConditions ()
    {
        // Prompt the user for the matrix size, player score, and win condition
        // Validate the input and create a new GameConditions object
    
        // Prompt the user for the player score
        System.out.print("Enter the player score (default 15): ");
        int playerScore = Integer.parseInt( System.console().readLine() );

        // Prompt the user for the matrix size
        System.out.print("Enter the matrix size (default 15): ");
        int matrixSize = Integer.parseInt( System.console().readLine() );

        // Prompt the user for the win condition
        System.out.print("Enter the win condition (default 5): ");
        int winCondition = Integer.parseInt( System.console().readLine() );

        try
        {
            validateWinConditions(playerScore, matrixSize, winCondition) ;

            // If the input is valid, create a new GameConditions object
            return new GameConditions(playerScore, matrixSize, winCondition); 
        } 
        catch ( IllegalArgumentException e ) 
        {
            System.out.println(e.getMessage()) ;
            return customeConditions() ; // Retry with new input
        }
    }

    /** 
     * @description toString method for GameConditions class
     * @return String representation of the GameConditions object
     */
    public String toString ()
    {
        return "GameConditions [playerScore=" + playerScore + " | MatrixSize=" + MatrixSize + "| winCondition=" + winCondition + "]" ;
    }
}

