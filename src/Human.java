package src;

import java.util.Scanner;

/**
 * Humain class representing a human player in the game.
 * It extends the User class.
 * 
 * @author ELNASORY Karam
 */
public class Human extends User {

    /**
     * Constructor for Humain class, meant to be used to create a
     *              custom player.
     * @param name  The name of the player.
     * @param score The score of the player.
     * @param color The color of the player.
     */
    public Human(String name, int score, Color color) {
        super(name, score, color);
    }

    /**
     * This constructor is meant to be used with
     * @param score to be parametered if we want to change the game initial
     *              conditions.
     */
    public Human(int score) {
        super(promptForName(), score, promptForColor());
    }

    /**
     * Method to prompt the user for their name.
     * @return The name of the player as a String.
     */
    public static String promptForName() {
        System.out.print("Enter your name: ");
        String name = System.console().readLine();
        return (name != null && !name.trim().isEmpty()) ? name : "Unknown";
    }

    /**
     * Prompts the user for a color.
     */
    public static Color promptForColor() {
        System.out.print("Choose a color: ");
        Color.displayAvailableColors();

        try {
            String colorInput = System.console().readLine();
            if (colorInput == null)
                return Color.RED; // Default
            int colorIndex = Integer.parseInt(colorInput);
            Color[] colors = Color.values();

            if (colorIndex >= 1 && colorIndex < colors.length && colors[colorIndex - 1] != Color.WHITE) {
                return colors[colorIndex - 1];
            }
            System.out.println("Invalid selection! Try again.");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
        }
        return promptForColor();
    }

    /**
     * Method to choose a placement on the board.
     *              Handles input validation and allows returning (-1, -1) for menu
     *              access.
     */
    @Override
    public Coordonates chosePlacement(Matrix matrix) {
        System.out.println(matrix.toString());
        System.out.print(this.name() + " (" + this.color() + ") : Enter your move (Row Column) or '-1 -1' for menu: ");

        String input = System.console().readLine();
        if (input == null)
            return new Coordonates(-1, -1);

        String[] parts = input.trim().split("\\s+");
        Coordonates placement = null;

        if (parts.length == 2) {
            try {
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                placement = new Coordonates(x, y);

                // Menu signal
                if (x == -1 && y == -1) {
                    return placement;
                }

                // Check validity
                // Note: isValidMove throws IllegalArgumentException if invalid
                if (matrix.isValidMove(placement)) {
                    return placement;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter two integers.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Invalid move: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid input format. Expected: 'Row Column'");
        }

        // Loop by recursion (simple retry)
        return chosePlacement(matrix);
    }
}
