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
    }
    // Player setup
    System.out.println("=== Player 1 Setup ===");
    User player1 = createPlayer(scanner, 1);
    System.out.println("\n=== Player 2 Setup ===");
    User player2 = createPlayer(scanner, 2);
    
    User currentPlayer = player1;
    boolean gameOver = false;
    
}