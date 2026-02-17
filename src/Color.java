package src;

/**
 * @author Syrine BEN HASSINE 
 * This class represents available player colors in the game
 */

/**
 * Enum representing the different player colors, each with an ANSI color code,
 * a Unicode symbol, and an abbreviation.
 */
public enum Color {
    BLUE("\u001B[34m", "\u25A0", "B"),
    GREEN("\u001B[32m", "\u25A0", "G"),
    YELLOW("\u001B[33m", "\u25A0", "Y"),
    PURPLE("\u001B[35m", "\u25A0", "P"),
    ORANGE("\u001B[38;5;208m", "\u25A0", "O"),
    WHITE("\u001B[37m", "\u25A1", "W"),
    RED("\u001B[31m", "\u25A0", "R");

    private final String ansiCode;
    private final String symbol;
    private final String abbr;

    // Detect OS
    private static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");

    /**
     * Constructor for the Color enum.
     * 
     * @param ansiCode The ANSI escape code for the color.
     * @param symbol   The Unicode symbol representing the color.
     * @param abbr     The ASCII abbreviation for the color.
     */
    Color(String ansiCode, String symbol, String abbr) {
        this.ansiCode = ansiCode;
        this.symbol = symbol;
        this.abbr = abbr;
    }

    /**
     * Returns a textual representation of the color adapted to the OS:
     * ASCII abbreviation on Windows, colored symbol on Mac/Linux.
     * 
     * @return A string representing the color for user display.
     */
    @Override
    public String toString() {
        if (IS_WINDOWS) {
            return ansiCode + abbr + "\u001B[0m";
        } else {
            return ansiCode + symbol + "\u001B[0m";
        }
    }

    /**
     * Displays the list of available colors in the terminal.
     * adapt to OS
     */
    public static void displayAvailableColors() {
        System.out.println("Available colors:");
        int index = 1;
        for (Color color : Color.values()) {
            if (color != WHITE) { // Allow RED, Computer will pick another
                System.out.printf("%d. %s (%s)%n", index++, color.toString(), color.name());
            }
        }
    }
}
