package src;

import java.io.IOException;

/**
 * KLauncher class representing the launcher for the game.
 * It contains the main method to start the game.
 * @author ELNASORY Karam + Syrine BEN HASSINE 
 */

 //add computer affichage , welcome message , 
public class KLauncher 
{
    private Game game ; // The on going game
    private GameConditions gameConditions ; // The game conditions

    /**
     * Constructor 
     */
    public KLauncher () 
    {
        // Constructor for KLauncher class
        this.game = null ;
        this.gameConditions = new GameConditions() ; // Default game conditions
    }

    /**
     * Displays a welcome message at the start of the game.
     */
    private void printWelcomeMessage() {
        System.out.println("=====================================");
        System.out.println("🎮  Welcome to GOMOKU GAME !!");
        System.out.println("=====================================");
        System.out.println("Get ready to challenge a friend or the computer!");
        System.out.println();
    }

    /**
     * @description Main menu
     */
    public void mainMenu ()
    {
        printWelcomeMessage();
        System.out.println("Main Menu") ;
        System.out.println("1. Start Game") ;
        System.out.println("2. Load Game") ;
        System.out.println("3. Settings (Setup a custome game conditions)") ; // IDEA
        System.out.println("4. Exit") ;
        System.out.print("Enter your choice: ") ;

        int choice = Integer.parseInt(System.console().readLine()) ;

        switch (choice) 
        {
            case 1:
                this.startingMenu() ;
                break ;

            case 2:
                this.game = Game.loadGame() ;
                this.game.start() ;
                break ;

            case 3:
                System.out.println("Current game conditions:") ;
                System.out.println(this.gameConditions.toString()) ;
                System.out.println("Enter the new game conditions:") ;

                this.gameConditions = GameConditions.customeConditions() ;
                mainMenu();

                break ;

            case 4:
                System.exit(0) ;
                break ;

            default:
                System.out.println("Invalid choice") ;
                mainMenu() ;
                break ;
        }
    }

    /**
     * @description Starting menu
     */
    public void startingMenu ()
    {
        System.out.println("Starting Menu") ;
        System.out.println("1. Player VS Player") ;
        System.out.println("2. Player VS Computer") ;
        System.out.println("3. Back to Main Menu") ;
        System.out.print("Enter your choice: ") ;

        int choice = Integer.parseInt(System.console().readLine()) ;

        switch (choice) 
        {
            case 1:
                this.game = new Game(this.gameConditions) ; // Player vs Player
                this.game.start() ;
                break ;

            case 2:
                this.game = new Game(this.gameConditions) ; // Player vs Computer // TODO : use an appropriate constructor
                this.game.start() ;
                break ;

            case 3:
                mainMenu() ;
                break ;

            default:
                System.out.println("Invalid choice") ;
                startingMenu() ;
                break ;
        }
    }

    /** 
     * @description Handles the inGame menu.
     */
    public void inGameMenu ()
    {
        System.out.println("In Game Menu") ;
        System.out.println("1. Resume Game") ;
        System.out.println("2. Save Game") ;
        System.out.println("3. Exit without saving") ;
        System.out.print("Enter your choice: ") ;

        int choice = Integer.parseInt(System.console().readLine()) ;

        switch (choice) 
        {
            case 1:
                this.game.continu()  ;

            case 2:
                try 
                {
                    this.game.save() ;
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
                break ;

            case 3:
                System.exit(0) ;
                break ;

            default:
                System.out.println("Invalid choice") ;
                inGameMenu() ;
                break ;
        }
    }

    /**
     * @description Main function to start the game 
     */
    public static void main (String [] args) 
    {
        KLauncher launcher = new KLauncher() ;
        launcher.mainMenu(); 
    }
}


