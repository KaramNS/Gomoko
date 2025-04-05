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
}