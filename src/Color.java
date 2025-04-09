package src;
import java.util.Scanner;

/**
 * @author Syrine-BEN HASSINE
 * @description This class represents available player colors in the game
 */
public enum Color {
    RED("\u001B[31m■"),    
    BLUE("\u001B[34m■"),   
    GREEN("\u001B[32m■"),  
    YELLOW("\u001B[33m■"), 
    PURPLE("\u001B[35m■"), 
    ORANGE("\u001B[38;5;208m■"), 
    BLACK("\u001B[30m■"),  
    WHITE("\u001B[37m□");

    private final String ansiCode;

    Color(String ansiCode) {
        this.ansiCode = ansiCode + "\u001B[0m";
    }

    @Override
    public String toString() {
        return this.ansiCode;
    }


    public static void displayAvailableColors() {
        System.out.println("Available colors:");
        int index = 1;
        for (Color color : Color.values()) {
            if (!color.name().equals("WHITE")) {
                System.out.printf("%d. %s (%s)%n", index++, color, color.name());
            }
        }
    }

    public static Color chooseColor(Scanner scanner, int playerNumber) {
        Color[] colors = Color.values();
        displayAvailableColors();
        while (true) {
            try {
                System.out.print("Select color for Player " + playerNumber + " (1-" + (colors.length - 1) + "): ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice < colors.length && colors[choice - 1] != WHITE) {
                    return colors[choice - 1];
                }
                System.out.println("Invalid selection! Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

}
