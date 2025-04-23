package src ;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

    // private final ArrayList< User > players ; // IDEA

    private final Matrix matrix ; // final ? 

    /**
     * @description Constructor for Game class, For testing purposes
     */
    public Game ()
    {
        this.player1 = new Human(Color.PURPLE) ;
        this.player2 = new Computer(Color.YELLOW) ;

        this.matrix = new Matrix(15, 5) ;
    }

    /**
     * @description Constructor for Game class, Human vs Human
     * @param player1 the first player
     * @param player2 the second player
     */
    public Game ( Human player1, Human player2 )
    {
        this.player1 = player1 ;
        this.player2 = player2 ;

        this.matrix = new Matrix(15, 5) ;
    }

    /**
     * @description Constructor for Game class, Human vs Computer
     * @param player
     */
    public Game (Human player)
    {
        this.player1 = player ;
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
     * @description Load a Game instance from a file
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
     * @description Discover the saved games in the directory "SavedGames", and display them to the user.
     */
    public static void discoverSavedGames ()
    {
        File directory = new File("./SavedGames/") ;

        File[] files = directory.listFiles() ;

        if ( files != null && files.length > 0 )
        {
            System.out.println("Saved games:") ;
            for ( File file : files )
            {
                if ( file.isFile() )
                {
                    System.out.println( file.getName() ) ;
                }
            }
        }
        else
        {
            System.out.println("No saved games found.") ;
        }
    }

    /**
     * @description Place the first token in the center of the matrix
     * @param startingUser the first user to play
     */

    public void placeFirstToken (User startingUser)
    {
        int center = ( this.matrix.getLength() / 2 ) + 1 ;
        System.out.println(startingUser.toString() + " Starts \n") ;
        this.matrix.putToken( new Coordonates(center, center), startingUser.token() ) ;
    }
    
    /**
     * @description Start the game 
     */
    public User start () // TODO : return a boolean false to go to in game menu, true 
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

        // Swicht currentUser
        nextUser = ( nextUser == this.player1 ) ? this.player2 : this.player1 ;

        clear() ;
        System.out.println( this.matrix.toString() ) ;
        // TODO : chosePlacement(Matrix matrix) and handls invalid tokens placement 
        while ( ! this.matrix.putToken(nextUser.chosePlacement(), nextUser.token() ) && nextUser.haveTokens() )
        {
            nextUser = ( nextUser == this.player1 ) ? this.player2 : this.player1 ;
            clear () ;
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
        discoverSavedGames();
        Game game = new Game();
        game.start() ;
    }
}