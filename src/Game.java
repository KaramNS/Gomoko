package src ;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Game class representing a game of Gomoku.
 * It contains the players and the game board (Matrix).
 * @author ELNASORY Karam
 */

public class Game implements Serializable
{
    private final User player1 ;
    private final User player2 ;

    private final Matrix matrix ; // final ? 

    /**
     * @description Constructor for Game class, 
     */
    public Game ()
    {
        this.player1 = new Human(Color.PURPLE) ;
        this.player2 = new Computer(Color.YELLOW) ;

        this.matrix = new Matrix() ;
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


    public void placeFirstToken (User statingUser)
    {
        int center = this.matrix.getLength() / 2 ;
    }
    
    /**
     * @description Start the game 
     */
    public void start () 
    {
        
    }
}
