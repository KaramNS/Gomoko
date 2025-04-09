package src ;

/**
 * Humain class representing a human player in the game.
 * It extends the User class.
 * @author ELNASORY Karam
 */

public class Human extends User 
{

    /**
     * @description Constructor for Humain class, meant to be used for already nown players.
     * @param name The name of the player.
     * @param score The score of the player.
     */
    public Human (String name, int score, Color color) 
    {
        super(name, score, color) ;
    }

    public Human (String name, Color color) 
    {
        super(name, 0, color) ;
    }

    /**
     * @description Constructor for Humain class, meant to be used for new players.
     * Will prompt the user for their name, and init the score to 0.
     */
    public Human (Color color)
    {
        super (promptForName(), 0, color) ;
    }

    /**
     * @description Method to prompt the user for their name.
     * @return The name of the player as a String.
     */
    public static String promptForName () 
    {
        System.out.print("Enter your name: ") ;
        String name = System.console().readLine() ;
        return name ;
    }

    /**
     * Method to choose a placement on the board to play a move (place a token).
     * @return Coordonates object representing the chosen placement.
     */
    @Override
    public Coordonates chosePlacement () 
    {
        System.out.print("Enter your move (x y): ") ;
        String input = System.console().readLine() ;

        String[] parts = input.split("//d+ //d+") ;

        if (parts.length == 2) 
        {
            try 
            {
                int x = Integer.parseInt(parts[0]) ;
                int y = Integer.parseInt(parts[1]) ;
                return new Coordonates(x, y) ;
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Invalid input. Please enter two integers.") ;

                return chosePlacement() ; // Retry the input
            }
        } 
        else 
        {
            System.out.println("Invalid input. Please enter two integers.") ;

            return chosePlacement() ; // Retry the input
        }
    } 

}
