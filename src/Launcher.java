package src;
import java.util.Scanner;

/**
 * @author Syrine-BEN HASSINE
 * @description This class manages the launching of the game, can acte  
 */

public class Launcher {
    private int winCondition;
    private int size;
    private final Scanner scanner;
    private Matrix board;
    private User player1;
    private User player2;
    private User currentPlayer;
    private Game game;

    // Constructeur qui fait lancer le jeu
    public Launcher() {
        this.size = 15;
        this.winCondition = 5;
        this.scanner = new Scanner(System.in);

        this.launch();
    }

    // Lancer tout le jeu
    public void launch() {
        printWelcomeMessage();
        int mode = chooseGameMode();

        board = new Matrix(size, winCondition);
        board.setNeighbors();
        setupBoard();
        setupPlayers(mode);
        game = new Game(); 
        game.start();
        scanner.close();
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
     * Lets the user choose the game mode (vs player or vs computer).
     * 
     * @return 1 for human vs human, 2 for human vs computer
     */
    private int chooseGameMode() {
        System.out.println("Choose your game mode:");
        System.out.println("1 - Play against another player");
        System.out.println("2 - Play against the computer");
    
        int choice = 0;
        while (choice != 1 && choice != 2) {
            System.out.print("Enter 1 or 2: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
            }
        }
        return choice;
    }

    /**
     * Displays the board configuration (size and win condition).
     */
    private void setupBoard() {
        System.out.println("\n=== Game Setup ===");
        System.out.println("Board Size: " + size + "x" + size);
        System.out.println("Win Condition: " + winCondition + " in a row");
    }

    /**
     * Sets up players based on the chosen game mode.
     * 
     * @param mode 1 for player vs player, 2 for player vs computer
     */
    private void setupPlayers(int mode) {
        System.out.println("\n=== Player 1 Setup ===");
        player1 = createPlayer(1);
    
        if (mode == 1) {
            System.out.println("\n=== Player 2 Setup ===");
            player2 = createPlayer(2);
            
            while (player2.color() == player1.color()) {
                System.out.println("This color is already taken. Please choose another one.");
                player2 = createPlayer(2);
            }    
        }else {
            Color computerColor = (player1.color() == Color.YELLOW) ? Color.PURPLE : Color.YELLOW;
            player2 = new Computer(computerColor);  
            System.out.println("Computer will play with color: " + computerColor);
        }
    
        currentPlayer = player1;
    }
    
    

    private User createPlayer(int playerNumber) {
        System.out.print("Enter name for Player " + playerNumber + ": ");
        String name = scanner.nextLine();
        Color color = Color.chooseColor(scanner, playerNumber);
        return new Human(name, 0, color);
    }
    

    private void afficherBoard() {
        System.out.println("\n=== Current Board ===");
        System.out.println(board);
    }

    
    private Coordonates getValidMove() {
        while (true) {
            try {
                System.out.print("Enter row (1-" + size + "): ");
                int row = Integer.parseInt(scanner.nextLine()) - 1;
                System.out.print("Enter column (1-" + size + "): ");
                int col = Integer.parseInt(scanner.nextLine()) - 1;
                
                Coordonates coord = new Coordonates(row, col);
                if (board.isValidMove(coord)) { 
                    return coord;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter numbers only.");
            }
        }
    }
    /*
     * 
     private void gameLoop() {
        boolean gameOver = false;
        
        while (!gameOver) {
            afficherBoard();
            System.out.println("\n" + currentPlayer.name() + "'s turn (" + currentPlayer.color() + ")");
            Coordonates move = getValidMove();
            
            if (board.putToken(move, new Token(currentPlayer.color()))) {
                afficherBoard();
                System.out.println("\n" + currentPlayer.name() + " wins!");
                gameOver = true;
            } else if (board.isBoardFull()) {  // Simplified check for draw
                afficherBoard();
                System.out.println("\nIt's a draw!");
                gameOver = true;
            } else {
                currentPlayer = (currentPlayer == player1) ? player2 : player1;
            }
        }
    }
    */
}
