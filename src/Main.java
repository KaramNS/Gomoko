package src;
import java.util.Scanner;

/**
 * @author Syrine-BEN HASSINE
 * @description This class represents the main program that allows players to play 
 * alternately on the board. 
 */

 public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int size = args.length > 0 ? Integer.parseInt(args[0]) : 15;
        int winCondition = args.length > 1 ? Integer.parseInt(args[1]) : 5;
        
        Matrix board = new Matrix(size, winCondition);
        board.setNeighbors();
        
        System.out.println("=== Player 1 Setup ===");
        User player1 = createPlayer(scanner, 1);
        System.out.println("\n=== Player 2 Setup ===");
        User player2 = createPlayer(scanner, 2);
        
        User currentPlayer = player1;
        boolean gameOver = false;
        
        while (!gameOver) {
            System.out.println("\nCurrent Board:" + board);
            
            System.out.println("\n" + currentPlayer.name() + "'s turn (" + currentPlayer.color() + ")");
            Coordonates move = getValidMove(scanner, board, currentPlayer);
            
            board.putToken(move.x(), move.y(), new Token(currentPlayer.color()));
            
            if (board.checkIsWin()) {
                System.out.println("\nFinal Board:" + board);
                System.out.println("\n" + currentPlayer.name() + " wins!");
                gameOver = true;
            } else if (isBoardFull(board)) {
                System.out.println("\nFinal Board:" + board);
                System.out.println("\nIt's a draw!");
                gameOver = true;
            } else {
                currentPlayer = (currentPlayer == player1) ? player2 : player1;
            }
        }
        scanner.close();
    }

    // Keep the rest of the methods unchanged except remove printBoard()
    private static User createPlayer(Scanner scanner, int playerNumber) {
        System.out.print("Enter name for Player " + playerNumber + ": ");
        String name = scanner.nextLine();
        
        Color color = selectColor(scanner, playerNumber);
        return new Human(name, color);
    }

    private static Color selectColor(Scanner scanner, int playerNumber) {
        System.out.println("Available colors:");
        Color[] colors = Color.values();
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] != Color.WHITE) {
                System.out.println((i+1) + ". " + colors[i]);
            }
        }
        
        while (true) {
            System.out.print("Select color for Player " + playerNumber + " (1-" + colors.length + "): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice >= 1 && choice <= colors.length && colors[choice-1] != Color.WHITE) {
                return colors[choice-1];
            }
            System.out.println("Invalid selection!");
        }
    }

    private static Coordonates getValidMove(Scanner scanner, Matrix board, User player) {
        int size = board.getLength();
        
        while (true) {
            System.out.print("Enter row (1-" + size + "): ");
            int row = scanner.nextInt() - 1;
            System.out.print("Enter column (1-" + size + "): ");
            int col = scanner.nextInt() - 1;
            scanner.nextLine(); 
            
            if (row >= 0 && row < size && col >= 0 && col < size) {
                Cell cell = board.getCell(row, col);
                if (cell.getColor() == Color.WHITE) {
                    return new Coordonates(row, col);
                }
                System.out.println("That cell is already occupied!");
            } else {
                System.out.println("Invalid position! Please enter values between 1 and " + size);
            }
        }
    }

    private static boolean isBoardFull(Matrix board) {
        int size = board.getLength();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getCell(i, j).getColor() == Color.WHITE) {
                    return false;
                }
            }
        }
        return true;
    }
}

class Human extends User {
    public Human(String name, Color color) {
        super(name, 0, color);
    }

    @Override
    public Coordonates chosePlacement() {
        return null;
    }
}