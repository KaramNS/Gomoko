package src;
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

    
}