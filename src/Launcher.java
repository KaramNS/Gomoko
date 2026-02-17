package src;

import java.io.IOException;

/**
 * Launcher class representing the launcher for the game.
 * It contains the main method to start the game.
 * 
 * @author ELNASORY Karam
 */
public class Launcher {
    private Game game; // The on going game
    private GameConditions gameConditions; // The game conditions

    // ANSI Colors for UI
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\u001B[1m";

    /**
     * Constructor
     */
    public Launcher() {
        // Constructor for Launcher class
        this.game = null;
        this.gameConditions = new GameConditions(); // Default game conditions
    }

    /**
     * Clears the console screen.
     */
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println(System.lineSeparator().repeat(50)); // Fallback
        }
    }

    /**
     * Displays a styled header.
     */
    private void printHeader(String title) {
        clearConsole();
        System.out.println(CYAN + "=====================================" + RESET);
        System.out.println(BOLD + "       " + title + RESET);
        System.out.println(CYAN + "=====================================" + RESET);
        System.out.println();
    }

    /**
     * Displays a welcome message at the start of the game.
     */
    private void printWelcomeMessage() {
        printHeader("🎮  GOMOKU GAME  🎮");
        System.out.println(YELLOW + "   Get ready to challenge a friend" + RESET);
        System.out.println(YELLOW + "          or the computer!" + RESET);
        System.out.println();
    }

    /**
     * @description Main menu
     */
    public void mainMenu() {
        printWelcomeMessage();
        System.out.println(BOLD + "Main Menu:" + RESET);
        System.out.println("  1. " + GREEN + "Start Game" + RESET);
        System.out.println("  2. " + YELLOW + "Load Game" + RESET);
        System.out.println("  3. Settings (Custom game conditions)");
        System.out.println("  4. " + RED + "Exit" + RESET);
        System.out.println();
        System.out.print("Enter your choice: ");

        try {
            String input = System.console().readLine();
            if (input == null)
                return;
            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    this.startingMenu();
                    break;
                case 2:
                    this.game = Game.loadGame();
                    if (this.game != null) {
                        this.game.start();
                    } else {
                        System.out.println("Press Enter to return to menu...");
                        System.console().readLine();
                        mainMenu();
                    }
                    break;
                case 3:
                    clearConsole();
                    System.out.println(CYAN + "=== Settings ===" + RESET);
                    System.out.println("Current game conditions:");
                    System.out.println(this.gameConditions.toString());
                    System.out.println();
                    System.out.println("Enter the new game conditions:");
                    this.gameConditions = GameConditions.customeConditions();
                    mainMenu();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println(RED + "Invalid choice!" + RESET);
                    Thread.sleep(1000);
                    mainMenu();
                    break;
            }
        } catch (NumberFormatException | InterruptedException e) {
            System.out.println(RED + "Invalid input!" + RESET);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
            }
            mainMenu();
        }
    }

    /**
     * @description Starting menu
     */
    public void startingMenu() {
        printHeader("⚔️  START GAME  ⚔️");
        System.out.println("  1. Player VS Player");
        System.out.println("  2. Player VS Computer");
        System.out.println("  3. Back to Main Menu");
        System.out.println();
        System.out.print("Enter your choice: ");

        try {
            String input = System.console().readLine();
            if (input == null)
                return;
            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    this.game = new Game(this.gameConditions); // Player vs Player
                    this.game.start();
                    break;
                case 2:
                    this.game = new Game(this.gameConditions); // Player vs Computer
                    this.game.start();
                    break;
                case 3:
                    mainMenu();
                    break;
                default:
                    System.out.println(RED + "Invalid choice!" + RESET);
                    Thread.sleep(1000);
                    startingMenu();
                    break;
            }
        } catch (NumberFormatException | InterruptedException e) {
            System.out.println(RED + "Invalid input!" + RESET);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
            }
            startingMenu();
        }
    }

    /**
     * @description Handles the inGame menu.
     */
    public void inGameMenu() {
        System.out.println("\n" + CYAN + "=== In-Game Menu ===" + RESET);
        System.out.println("  1. Resume Game");
        System.out.println("  2. Save Game");
        System.out.println("  3. " + RED + "Exit without saving" + RESET);
        System.out.println();
        System.out.print("Enter your choice: ");

        try {
            String input = System.console().readLine();
            if (input == null)
                return;
            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    this.game.continu();
                    break;
                case 2:
                    try {
                        this.game.save();
                        System.out.println(GREEN + "Game saved successfully!" + RESET);
                    } catch (IOException e) {
                        System.out.println(RED + "Error saving game: " + e.getMessage() + RESET);
                    }
                    System.out.println("Press Enter to continue...");
                    System.console().readLine();
                    this.game.continu();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println(RED + "Invalid choice!" + RESET);
                    inGameMenu();
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "Invalid input!" + RESET);
            inGameMenu();
        }
    }
}
