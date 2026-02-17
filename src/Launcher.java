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
     * Displays a Gomoko logo in the console.
     */
    private void printGomokoLogo() {
        System.out.println(GREEN +
                "\n" +
                "       _______   ______   .___  ___.   ______    __  ___   ______   \n" +
                "      /  _____| /  __  \\  |   \\/   |  /  __  \\  |  |/  /  /  __  \\  \n" +
                "     |  |  __  |  |  |  | |  \\  /  | |  |  |  | |  '  /  |  |  |  | \n" +
                "     |  | |_ | |  |  |  | |  |\\/|  | |  |  |  | |    <   |  |  |  | \n" +
                "     |  |__| | |  `--'  | |  |  |  | |  `--'  | |  .  \\  |  `--'  | \n" +
                "      \\______|  \\______/  |__|  |__|  \\______/  |__|\\__\\  \\______/  \n" +
                "\n" +
                RESET);
    }

    /**
     * Displays a welcome message at the start of the game.
     */
    private void printWelcomeMessage() {
        clearConsole();
        printGomokoLogo();
        System.out.println(CYAN + "     🎮 Welcome to GOMOKU GAME ! 🎮" + RESET);
        System.out.println(CYAN + "=====================================" + RESET);
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
                        handleGameLoop(false); // False means RESUME (call continu)
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
                    handleGameLoop(true); // True means START
                    break;
                case 2:
                    // Player vs Computer
                    // Note: Game(GameConditions) provides P2 as Computer by default in our updated
                    // Game.java
                    this.game = new Game(this.gameConditions);
                    handleGameLoop(true);
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
     * Helper to manage game flow loop.
     * 
     * @param isNewGame true if calling start(), false if calling continu()
     */
    private void handleGameLoop(boolean isNewGame) {
        boolean isGameOver;
        try {
            if (isNewGame) {
                isGameOver = this.game.start();
            } else {
                isGameOver = this.game.continu();
            }

            if (isGameOver) {
                // Game ended clearly (win or out of tokens)
                System.out.println("\nPress Enter to return to Main Menu...");
                System.console().readLine();
                mainMenu();
            } else {
                // Determine interruption (menu requested)
                inGameMenu();
            }
        } catch (Exception e) {
            System.out.println(RED + "An error occurred: " + e.getMessage() + RESET);
            inGameMenu();
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
                    handleGameLoop(false); // Resume
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
                    handleGameLoop(false); // Resume
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
