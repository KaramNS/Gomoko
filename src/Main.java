package src;
import java.util.Scanner;

/**
 * @author Syrine-BEN HASSINE
 * @description This class represents the main program that allows players to play 
 * alternately on the board. 
 * It handles player setup, board initialization, checks for win conditions 
 */

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the board size and win condition from arguments or use defaults
        int size = args.length > 0 ? Integer.parseInt(args[0]) : 15;
        int winCondition = args.length > 1 ? Integer.parseInt(args[1]) : 5;

        // Initialize the game board
        Matrix board = new Matrix(size, winCondition);
        board.setNeighbors(); // Set up neighbors for each cell

        System.out.println("\n=== Game Setup ===");
        System.out.println("Board Size: " + size + "x" + size);
        System.out.println("Win Condition: " + winCondition + " in a row");

        // Setup the players
        System.out.println("=== Player 1 Setup ===");
        User player1 = createPlayer(scanner, 1);
        System.out.println("\n=== Player 2 Setup ===");
        User player2 = createPlayer(scanner, 2);

        User currentPlayer = player1; // Start with Player 1
        boolean gameOver = false;
        int moveCount = 0;

        // Game loop
        while (!gameOver) {
            System.out.println("\n=== Current Board ===");
            System.out.println(board); // Print the current board before making a move

            System.out.println("\n" + currentPlayer.name() + "'s turn (" + currentPlayer.color() + ")");
            Coordonates move = getValidMove(scanner, board);

            // Place the token for the current player
            board.putToken(move.x(), move.y(), new Token(currentPlayer.color()));
            moveCount++;

            // Print the board after the move
            System.out.println("\n=== Updated Board ===");
            System.out.println(board); // Print the updated board with the placed token

            // Check game state
            if (board.checkIsWin()) {
                System.out.println("\nFinal Board:");
                System.out.println(board);
                System.out.println("\n" + currentPlayer.name() + " wins!");
                gameOver = true;
            } else if (moveCount >= size * size || board.isBoardFull()) {
                System.out.println("\nFinal Board:");
                System.out.println(board);
                System.out.println("\nIt's a draw!");
                gameOver = true;
            } else {
                currentPlayer = (currentPlayer == player1) ? player2 : player1; // Switch player
            }
        }

        scanner.close();
    }

    private static User createPlayer(Scanner scanner, int playerNumber) {
        System.out.print("Enter name for Player " + playerNumber + ": ");
        String name = scanner.nextLine();
        Color color = Color.chooseColor(scanner, playerNumber);
        return new Human(name, 0, color);
    }

    

    private static Coordonates getValidMove(Scanner scanner, Matrix board) {
        int size = board.getLength();
        while (true) {
            try {
                System.out.print("Enter row (1-" + size + "): ");
                int row = Integer.parseInt(scanner.nextLine()) - 1;
                System.out.print("Enter column (1-" + size + "): ");
                int col = Integer.parseInt(scanner.nextLine()) - 1;

                if (row >= 0 && row < size && col >= 0 && col < size) {
                    Cell cell = board.getCell( new Coordonates(row, col) );
                    if (cell.getColor() == Color.WHITE) {
                        return new Coordonates(row, col);
                    }
                    System.out.println("That cell is already occupied! Try another.");
                } else {
                    System.out.println("Invalid position! Please enter values between 1 and " + size);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter numbers only.");
            }
        }
    }
}