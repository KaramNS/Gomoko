package src;

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
 * 
 * @author ELNASORY Karam
 */

public class Game implements Serializable {
    private final User player1;
    private final User player2;

    private User nextUser;

    private final Matrix matrix; // final ?

    private final GameConditions gameConditions; // TODO Standarized the usage of this class

    /**
     * Constructor for Game class, Human vs Human
     * @param player1 the first player
     * @param player2 the second player
     */
    public Game(Human player1, Human player2) // TODO add game conditions in param

    {
        this.player1 = player1;
        this.player2 = player2;

        this.gameConditions = new GameConditions(); // Default game conditions
        this.matrix = new Matrix(this.gameConditions);
    }

    /**
     * Constructor for Game class
     * @param gameConditions
     */
    public Game(GameConditions gameConditions) {
        this.player1 = new Human(gameConditions.playerScore());
        this.player2 = new Computer(gameConditions.playerScore(), Color.RED); // Use Computer

        this.gameConditions = gameConditions;
        this.matrix = new Matrix(this.gameConditions);
    }

    /**
     * Constructor for Game class with specific opponent
     */
    public Game(GameConditions gameConditions, User p2) {
        this.player1 = new Human(gameConditions.playerScore());
        this.player2 = p2;

        this.gameConditions = gameConditions;
        this.matrix = new Matrix(this.gameConditions);
    }

    /**
     * Save the instance in a file
     * @throws IOException
     */
    public void save() throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = formatter.format(Calendar.getInstance().getTime());

        String player1Name = player1.name().replaceAll("[^a-zA-Z0-9_-]", "_");
        String player2Name = player2.name().replaceAll("[^a-zA-Z0-9_-]", "_");

        String fileName = timestamp + "_" + player1Name + "_vs_" + player2Name + ".sav";
        String destination = "./SavedGames/" + fileName;

        File dir = new File("./SavedGames/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(destination))) {
            out.writeObject(this);
        }
    }

    /**
     * Load a Game instance from a file
     */
    public static Game load(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("./SavedGames/" + fileName))) {
            Game game = (Game) in.readObject();
            return game;
        } catch (Exception e) {
            System.err.println(e);
        }

        return null;
    }

    /**
     * Discover the saved games in the directory "SavedGames"
     */
    public static File[] discoverSavedGames() {
        File directory = new File("./SavedGames/");

        File[] files = directory.listFiles();

        int fileIndex = 0;
        if (files != null && files.length > 0) {
            System.out.println("Saved games:");
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(++fileIndex + ". " + file.getName());
                }
            }
        } else {
            System.out.println("No saved games found.");
        }
        return files;
    }

    /**
     * Load a game from the saved games
     */
    public static Game loadGame() {
        File[] files = Game.discoverSavedGames();

        if (files == null || files.length == 0)
            return null;

        System.out.print("Enter the number of the game you want to load: ");
        try {
            int choice = Integer.parseInt(System.console().readLine());
            if (choice < 1 || choice > files.length) {
                System.out.println("Invalid choice");
                return null;
            }
            String fileName = files[choice - 1].getName();

            Game game = Game.load(fileName);
            if (game == null) {
                System.out.println("Error loading game");
                return null;
            } else {
                System.out.println("Game loaded successfully");
                return game;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            return null;
        }
    }

    /**
     * Place the first token in the center of the matrix
     */
    private void placeFirstToken(User startingUser) {
        int center = (this.matrix.getLength() / 2);
        System.out.println(startingUser.toString() + " Starts \n");
        this.matrix.putToken(new Coordonates(center, center), startingUser.token());
    }

    /**
     * Start the game
     * Returns true if game over, false if interrupted (menu)
     */
    public boolean start() {
        Random random = new Random();
        int starterChoice = random.nextInt(2);

        if (starterChoice == 0) {
            this.nextUser = this.player1;
        } else {
            this.nextUser = this.player2;
        }

        this.placeFirstToken(this.nextUser);

        // Switch currentUser
        this.nextUser = (this.nextUser == this.player1) ? this.player2 : this.player1;

        return this.continu();
    }

    /**
     * Continue the game until one of the players wins or there are no
     *              more tokens left
     * @return boolean: true if game over (win/draw), false if interrupted for menu
     */
    public boolean continu() {
        clear();

        while (this.nextUser.haveTokens()) {
            // 1. Choose placement
            Coordonates placement = this.nextUser.chosePlacement(this.matrix);

            // 2. Check menu signal (-1 -1)
            if (placement.x() == -1 && placement.y() == -1) {
                return false; // Request menu
            }

            // 3. Place token safely
            boolean isWin = false;
            try {
                isWin = this.matrix.putToken(placement, this.nextUser.token());
            } catch (Exception e) {
                System.out.println("Error placing token: " + e.getMessage());
                // Retrying logic or skip turn?
                // For now, if invalid, we might loop back, but Human checks validation.
                // If it fails here, it's serious.
                continue;
            }

            // 4. Check win
            if (isWin) {
                System.out.println("Player " + this.nextUser.name() + " won the game!");
                return true; // Game Over
            }

            // 5. Check tokens (if empty after placement, loop condition handles strictly?
            // No, check now)
            if (!this.nextUser.haveTokens()) {
                break; // Tie handling below
            }

            // 6. Switch User
            this.nextUser = (this.nextUser == this.player1) ? this.player2 : this.player1;

            clear();
        }

        // Out of loop means no more tokens or other break
        System.out.println("No more tokens left for " + this.nextUser.name() + " !");
        // Typically leads to a tie or opponent win by default?
        // Original logic returned opponent as winner?
        // "return ( this.nextUser == this.player1 ) ? this.player2 : this.player1 ;"
        // I'll just say Game Over.
        return true;
    }

    /**
     * Clear the console, to get a cleaner terminal
     */
    public static void clear() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Fail silently
        }
    }
}
