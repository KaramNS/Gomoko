package src ;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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

    private User nextUser ;

    // private final ArrayList< User > players ; // IDEA

    private final Matrix matrix ; // final ? 

    private final GameConditions gameConditions ; 

    /**
     * @description Constructor for Game class, For testing purposes
     */
    public Game () // TODO : remove this constructor
    {
        this.player1 = new Human(Color.GREEN) ;
        this.player2 = new Computer(Color.RED) ;

        this.matrix = new Matrix(15, 5) ;
        this.gameConditions = new GameConditions() ; // Default game conditions
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
        this.gameConditions = new GameConditions() ; // Default game conditions
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
        this.gameConditions = new GameConditions() ; // Default game conditions
    }

    /**
     * @description Constructor for Game class, ??????????????
     * @param gameConditions
     */
    public Game (GameConditions gameConditions)
    {

        // TODO : TO BE FINISHED
        this.player1 = new Human(gameConditions.getPlayerScore()) ;
        this.player2 = new Computer(Color.YELLOW) ;

        this.matrix = new Matrix(gameConditions.getMatrixSize(), gameConditions.getWinCondition()) ;
        this.gameConditions = gameConditions ; // Default game conditions
    }
    
    /**
     * @description Save the instance in a file
     * @throws IOException
     */
    public void save() throws IOException 
    {
        // Format the current date-time safely
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = formatter.format(Calendar.getInstance().getTime());

        // Clean player names to be safe in filenames (optional but good practice)
        String player1Name = player1.name().replaceAll("[^a-zA-Z0-9_-]", "_");
        String player2Name = player2.name().replaceAll("[^a-zA-Z0-9_-]", "_");

        // Construct safe filename
        String fileName = timestamp + "_" + player1Name + "_vs_" + player2Name + ".sav";
        String destination = "./SavedGames/" + fileName;

        // Create directory if it doesn't exist
        File dir = new File("./SavedGames/");
        if (!dir.exists()) 
        {
            dir.mkdirs();
        }

        // Write the object to file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(destination))) 
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
    public static File[] discoverSavedGames ()
    {
        File directory = new File("./SavedGames/") ;

        File[] files = directory.listFiles() ;

        int fileIndex = 0 ;
        if ( files != null && files.length > 0 )
        {
            System.out.println("Saved games:") ;
            for ( File file : files )
            {
                if ( file.isFile() )
                {
                    System.out.println( ++fileIndex + ". " + file.getName() ) ;
                }
            }
        }
        else
        {
            System.out.println("No saved games found.") ;
        }
        return files ;
    }

    /**
     * @description Load a game from the saved games
     * @return A Game instance
     */
    public static Game loadGame ()
    {
        File[] files = Game.discoverSavedGames();

        System.out.print("Enter the number of the game you want to load: ") ;
        int choice = Integer.parseInt(System.console().readLine()) ;
        if ( choice < 1 || choice > files.length )
        {
            System.out.println("Invalid choice") ;
            return null ;
        }
        String fileName = files[choice].getName() ;

        Game game = Game.load(fileName) ;
        if ( game == null )
        {
            System.out.println("Error loading game") ;
            return null ;
        }
        else
        {
            System.out.println("Game loaded successfully") ;
            return game ;
        }
    }

    /**
     * @description Place the first token in the center of the matrix
     * @param startingUser the first user to play
     */

    private void placeFirstToken (User startingUser)
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

        if ( starterChoice == 0 )
        {
            this.nextUser = this.player1 ;
        }
        else 
        {
            this.nextUser = this.player2 ;
        }
        
        this.placeFirstToken(this.nextUser) ; 

        // Swicht currentUser
        this.nextUser = ( this.nextUser == this.player1 ) ? this.player2 : this.player1 ;
        
        return this.continu() ;
    }

    /**
     * @description Continue the game until one of the players wins or there are no more tokens left
     * @return The user who won the game // TODO
     */
    public User continu ()
    {
        clear() ;
        // System.out.println( this.matrix.toString() ) ; // TODO : delete this line
        // chosePlacement(Matrix matrix) and handls invalid tokens placement DONE
        
        while ( ! this.matrix.putToken(this.nextUser.chosePlacement( this.matrix ), this.nextUser.token() ) && this.nextUser.haveTokens() )
        {
            this.nextUser = ( this.nextUser == this.player1 ) ? this.player2 : this.player1 ;
            clear () ;
            System.out.println( this.matrix.toString() ) ;
        }

        if ( this.nextUser.haveTokens() )
        {
            System.out.println("Player " + this.nextUser.name() + " won the game") ;
            return this.nextUser ;
        }
        else
        {
            System.out.println("No more tokens left for " + this.nextUser.name() + " !") ;
            return ( this.nextUser == this.player1 ) ? this.player2 : this.player1 ;
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
        // game.start() ;
        try 
        {
            game.save() ;
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}