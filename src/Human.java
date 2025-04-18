package src ;

/**
 * Humain class representing a human player in the game.
 * It extends the User class.
 * @author ELNASORY Karam
 */

public class Human extends User 
{
    /**
     * @description Constructor for Humain class, meant to be used for already known players.
     * @param name The name of the player.
     * @param score The score of the player.
     */
    public Human (String name, int score, Color color) 
    {
        super(name, score, color) ;
    }

    /**
     * @description Constructor for Humain class, meant to be used for new players.
     * @param name name of the player
     * @param color color of the player
     */
    public Human (String name, Color color) 
    {
        super(name, 15, color) ;
    }

    /**
     * @description Constructor for Humain class, meant to be used for new players.
     * Will prompt the user for their name, and init the score to 0.
     */
    public Human (Color color)
    {
        super (promptForName(), 15, color) ;
    }

    /**
     * @description This constructor is meant to be used with 
     * @param score to be parametered if we want to change the game initial conditions
     * @return
     */
    public Human (int score) 
    {
        super ( promptForName(), score, promptForColor() ) ;
    }

    /**
     * @description Method to prompt the user for their name.
     * @return The name of the player as a String.
     */
    public static String promptForName () 
    {
        System.out.print("Enter your name: ") ;

        String name = System.console().readLine() ; // TO TEST 

        return name ;
    }

    public static Color promptForColor ()
    {
        System.out.print("Choose a color: ") ;
        Color.displayAvailableColors() ;
        String colorInput = System.console().readLine() ;
        int colorIndex = Integer.parseInt(colorInput) ;

        Color[] colors = Color.values();

        try 
        {
            if (colorIndex >= 1 && colorIndex < colors.length && colors[colorIndex - 1] != Color.WHITE) 
            {
                return colors[colorIndex - 1];
            }
            System.out.println("Invalid selection! Try again.");
        } 
        catch (NumberFormatException e) 
        {
            System.out.println("Please enter a valid number!");
            return promptForColor() ; 
        }
        return null ; // This line should never be reached, it is just to satisfy the compiler and make the typing system 
    }

    /**
     * @description Method to choose a placement on the board to play a move (place a token).
     * @return Coordonates object representing the chosen placement.
     */
    @Override
    public Coordonates chosePlacement () 
    {
        System.out.print("Enter your move (x y): ") ;
        String input = System.console().readLine() ;

        String[] parts = input.split("\\s+") ;

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
