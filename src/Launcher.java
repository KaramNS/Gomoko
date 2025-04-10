package src;
import java.util.Scanner;

/**
 * @author Syrine-BEN HASSINE
 * @description This class represents 
 */

public class Launcher {
    private int winCondition;
    private int size;
    private final Scanner scanner;
    private Matrix board;
    private User player1;
    private User player2;
    private User currentPlayer;

    // Constructeur qui fait lancer le jeu
    public Launcher() {
        this.size = 15;
        this.winCondition = 5;
        this.scanner = new Scanner(System.in);

        this.launch();
    }

    // Lancer tout le jeu
    public void launch() {
        board = new Matrix(size, winCondition);
        board.setNeighbors();
        setupBoard();
        setupPlayers();
        gameLoop();
        scanner.close();
    }

    private void setupBoard() {
        System.out.println("\n=== Game Setup ===");
        System.out.println("Board Size: " + size + "x" + size);
        System.out.println("Win Condition: " + winCondition + " in a row");
    }

    private void setupPlayers() {
        System.out.println("=== Player 1 Setup ===");
        player1 = createPlayer(1);
        System.out.println("\n=== Player 2 Setup ===");
        player2 = createPlayer(2);
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
}
