package src ;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
// import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Game class representing a game of Gomoku.
 * It contains the players and the game board (Matrix).
 * @author ELNASORY Karam
 */

public class Game implements Serializable
{
    private final User player1 ;
    private final User player2 ;

    // private final ArrayList< User > players ; IDEA 

    private final Matrix matrix ; // final ? 

    /**
     * @description Constructor for Game class, 
     */
    public Game ()
    {
        this.player1 = new Human(Color.PURPLE) ;
        this.player2 = new Computer(Color.YELLOW) ;

        this.matrix = new Matrix(15, 5) ;
    }

    /**
     * @description Save the instance in a file
     * @throws IOException
     */
    public void save () throws IOException 
    {
        StringBuilder sb = new StringBuilder() ;
        sb.append( Calendar.getInstance().getTime().toString() ) ;

        sb.append(" ") ;
        sb.append( player1.name() ) ;
        sb.append(" ") ;
        sb.append( player2.name() ) ;

        String fileName = sb.toString() ;

        try ( ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( fileName ) ) ) 
        {
            out.writeObject(this);
        }
    }

    /**
     * 
     * @param fileName of the file that contains the Game object to be loaded 
     * @return A Game instance
     */
    public static Game load (String fileName)
    {
        try ( ObjectInputStream in = new ObjectInputStream( new FileInputStream( "./SavedGames/" + fileName) ) ) 
        {
            Game game = (Game) in.readObject() ;
            return game ;
        }
        catch (Exception e)
        {
            System.err.println(e);
        }

        return null ;
    }

    /**
     * @description Place the first token in the center of the matrix
     * @param statingUser
     */

    public void placeFirstToken (User startingUser)
    {
        int center = ( this.matrix.getLength() / 2 ) + 1 ;
        this.matrix.putToken( center, center, startingUser.token() ) ;
    }
    
    /**
     * @description Start the game 
     */
    public User start () 
    {
        Random random = new Random () ; 
        int starterChoice = random.nextInt(2) ;
        User nextUser ;

        if ( starterChoice == 0 )
        {
            nextUser = this.player1 ;
        }
        else 
        {
            nextUser = this.player2 ;
        }
        
        this.placeFirstToken(nextUser) ; 

        clear() ;
        System.out.println( this.matrix.toString() ) ;
        
        while ( ! this.matrix.putToken(nextUser.chosePlacement(), nextUser.token() ) && nextUser.haveTokens() )
        {
            nextUser = ( nextUser == this.player1 ) ? this.player2 : this.player1 ;
            System.out.println( this.matrix.toString() ) ;
        }

        if ( nextUser.haveTokens() )
        {
            System.out.println("Player " + nextUser.name() + " won the game") ;
            return nextUser ;
        }
        else
        {
            System.out.println("No more tokens left for " + nextUser.name() + " !") ;
            return ( nextUser == this.player1 ) ? this.player2 : this.player1 ;
        }
    }

    /**
     * @description Clear the console, to get a cleaner terminal
     * 
     */
    public static void clear() 
    {
        try 
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) 
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } 
        catch (final Exception e) 
        {
            System.out.println("Erreur lors de l'effacement de la console : " + e.getMessage());
        }
    }

    public static void main (String [] args)
    {
        Game game = new Game();
        game.start() ;
    }

}
