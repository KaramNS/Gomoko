package src;

/**
 * @author Jean-Baptiste + Syrine BEN HASSINE 
 * @descritption This class represents the  game board
 * the number of tokens needed to win can be set in the constructor  
 * (default is 5 in a row)
 */
public class Matrix {
    
    private Cell[][] grid;
    private int numberNeededToWin = 5;


    /*################### Constructors ##################### */

    /**
     * Default Constructor : Initialize the grid with a size of 15x15
     * The number required to win the game is set to 5
     */
    public Matrix(){
        this.numberNeededToWin = 5;
        this.grid = new Cell[15][15];
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; i++){
                this.grid[i][j] = new Cell();
            }
        }
    }

    /**
     * Constructor, initialize the grid with the  specified length 
     * the number required to win is set to 5
     * @param length the length of the matrix
     */
    public Matrix(int length){

        this.grid = new Cell[length][length];
        this.numberNeededToWin = 5;

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                this.grid[i][j] = new Cell();
            }
        }
        
    }

    /**
     * Constructor, initialize the grid with the specified length and the number
     * of tokens required to win
     * @param length The length of the matrix (square board)
     * @param numberWin the number of tokens in a row required to win
     */
    public Matrix(int length, int numberWin){
        this.grid = new Cell[length][length];
        this.numberNeededToWin = numberWin;

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                this.grid[i][j] = new Cell();
            }
        }
        
    }


    /*######## Getters/Setter ############*/

    /**
     *  set a cell in a position in the matrix
     * @param x is a the x axis
     * @param y is the y axis
     * @param cell is the cell you want to put in this slot
     */
    public void setGrid(int x, int y , Cell cell){
        this.grid[x][y] = cell;
    }


    /**
     * Gets the length of the squaer board
     * @return Length of the square board
     */
    public int getLength(){
        return this.grid[0].length;
    }

    @Override
    public String toString() {
        String s = "";
        int length = this.grid.length;


        for(int i = 0 ; i < length; i++) {

            s = s+ "\n";

            for(int j = 0 ; j < length ; j++){

                s = s + grid[i][j] + " ";

            }

        }
        s = s+ "\n";
        return s;
    }


    
    
    /**
     * Get the cell at the specified coordinates in the grid.
     * @param x coordinate (row index)
     * @param y coordinate (column index)
     */
    public Cell getCell(int x, int y) {
        return grid[x][y];
    }
    /**
     * Get the cell at the specified coordinates in the grid
     * @param coord The coordonates in the grid
     * @return
     */
    public Cell getCell(Coordonates coord){
        return grid[coord.x()][coord.y()];
    }
    
    
    
    /*################### Methods ################### */

    /**
     * Put a token in a specified position on the board.
     * @param xAxis The x axis 
     * @param yAxis The y axis
     * @param token The token you want to put in the specified position on board
     */
    public boolean  putToken(int xAxis, int yAxis, Token token){
        Coordonates coord = new Coordonates(xAxis,yAxis);
        isValidMove(coord);
        var currentCell = this.grid[coord.x()][coord.y()];
        currentCell.setToken(token);
        return currentCell.isWon(numberNeededToWin);
    }

    /**
     * Put a token in the game and check if it is won. If it is , 
     * return true.
     * @param coord The coordonates of the future token
     * @param token the token to put on the board
     * @return true if you win the game, false otherwise
     */
    public boolean putToken(Coordonates coord, Token token){
        
        
        if(isValidMove(coord)){
            var currentCell = this.grid[coord.x()][coord.y()];
            currentCell.setToken(token);
            return currentCell.isWon(numberNeededToWin);
        }
        else{
            return false;
        }
    }

    /**
     * Check if the entire board is full, meaning all cells are occupied.
     * @param board The Matrix representing the game board
     */
    public boolean isBoardFull(Matrix board) {
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
    /**
     * Check if you win the game
     * @return True if you win, false otherwise
     */
    public boolean checkIsWin(){


        for(Cell[] c : this.grid){
            for(Cell caseCell : c){
                if(caseCell.isWon( this.numberNeededToWin)){
                    return true;

                }
            }
        }

        return false;
    }

    public boolean checkIsWin(Cell cell){
        return cell.isWon(this.numberNeededToWin);
    }

    /**
     * Sets all valid neighbors for each cell in the grid.
     * Each cell will have its surrounding cells assigned according to the 8 directions.
     */
    public void setNeighbors() {
        int length = getLength();

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                Cell currentCell = grid[i][j];

                // check for UP
                if (i > 0) {
                    currentCell.setNeighbor(grid[i - 1][j], Direction.UP);
                }

                // check for DOWN
                if (i < length - 1) {
                    currentCell.setNeighbor(grid[i + 1][j], Direction.DOWN);
                }

                // check for LEFT
                if (j > 0) {
                    currentCell.setNeighbor(grid[i][j - 1], Direction.LEFT);
                }

                //check for  RIGHT
                if (j < length - 1) {
                    currentCell.setNeighbor(grid[i][j + 1], Direction.RIGHT);
                }

                //check for  UP_LEFT
                if (i > 0 && j > 0) {
                    currentCell.setNeighbor(grid[i - 1][j - 1], Direction.UP_LEFT);
                }

                //check for  UP_RIGHT
                if (i > 0 && j < length - 1) {
                    currentCell.setNeighbor(grid[i - 1][j + 1], Direction.UP_RIGHT);
                }

                //check for  DOWN_LEFT
                if (i < length - 1 && j > 0) {
                    currentCell.setNeighbor(grid[i + 1][j - 1], Direction.DOWN_LEFT);
                }

                //check for  DOWN_RIGHT
                if (i < length - 1 && j < length - 1) {
                    currentCell.setNeighbor(grid[i + 1][j + 1], Direction.DOWN_RIGHT);
                }
            }
        }
    }


    /**
     * Says if the move is valid, can throws a NumberFormatException
     * @param coord The coordonates you want to know if 
     * @return True if the move is valid, false otherwise
     */
    private boolean isValidMove(Coordonates coord)  {
        int size = this.getLength();
       
            try {
                if (coord.x() >= 0 && coord.x() < size && coord.y() >= 0 && coord.y() < size) {
                    Cell cell = this.getCell(coord.x(), coord.y());
                    if (cell.getColor() == Color.WHITE) {
                        return true;
                    }
                    System.out.println("That cell is already occupied! Try another.");
                    return false;
                } else {
                    System.out.println("Invalid position! Please enter values between 1 and " + size);
                    return false;
                
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter numbers only.");
                return false;
            }
        
    }


    // System.out.print("Enter row (1-" + size + "): ");
    // int row = Integer.parseInt(scanner.nextLine()) - 1;
    // System.out.print("Enter column (1-" + size + "): ");
    // int col = Integer.parseInt(scanner.nextLine()) - 1;



}
