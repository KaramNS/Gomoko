package src;

import java.io.Serializable;
import java.lang.*;
import java.util.ArrayList;


/**
 * @author Jean-Baptiste + Syrine BEN HASSINE 
 * @descritption This class represents the  game board
 * the number of tokens needed to win can be set in the constructor  
 * (default is 5 in a row)
 * The gameCondition object is used to initialize the game and keep in 
 * memory the win conditions
 */
public class Matrix implements Serializable {
    
    private final ArrayList<ArrayList<Cell>> grid;
    
    private final GameConditions gameConditions;


    /*################### Constructors ##################### */

    /**
     * @author Jean-Baptiste
     * Default Constructor : Initialize the grid with a size of 15x15
     * The number required to win the game is set to 5
     * @deprecated Use Matrix(GameConditions gameConditions1)
     */
    @Deprecated
    public Matrix(){
        this.gameConditions = new GameConditions();
        this.grid = new ArrayList<>();
        for(int i = 0; i < this.gameConditions.MatrixSize(); i++){
            this.grid.add(new ArrayList<>());
            for(int j = 0; j < this.gameConditions.MatrixSize(); j++){
                this.grid.get(i).add(new Cell());
            }
        }

        this.setNeighbors();
    }

    /**
     * @author Jean-Baptiste
     * Constructor, initialize the grid with the  specified length 
     * the number required to win is set to 5
     * @param length the length of the matrix
     * @deprecated Use Matrix(GameConditions gameConditions1)
     */
    @Deprecated
    public Matrix(int length){

        this.grid = new ArrayList<>();
        this.gameConditions = new GameConditions(15, length, 5);

        for(int i = 0; i < length; i++){
            this.grid.add(new ArrayList<>());
            for(int j = 0; j < length; j++){
                this.grid.get(i).add(new Cell());
            }
        }
        this.setNeighbors();
        
    }

    /**
     * @author Jean-Baptiste
     * Constructor, initialize the grid with the specified length and the number
     * of tokens required to win
     * @param length The length of the matrix (square board)
     * @param numberWin the number of tokens in a row required to win
     * @deprecated Use Matrix(GameConditions gameConditions1)
     */
    @Deprecated
    public Matrix(int length, int numberWin){
        this.grid = new ArrayList<>();
        this.gameConditions = new GameConditions(15, length, numberWin);
        for(int i = 0; i < length; i++){
            this.grid.add(new ArrayList<>());
            for(int j = 0; j < length; j++){
                this.grid.get(i).add(new Cell());
            }
        }
        this.setNeighbors();
    }

    /**
     * @author Jean-Baptiste
     * Constructor, initialize the grid with the specified length and the number
     * of tokens required to win and the number of tokens players have
     * @param length The length of the matrix (square board)
     * @param numberWin the number of tokens in a row required to win
     * @param playerScore is the number of tokens the players have in the start of the game
     * @deprecated Use Matrix(GameConditions gameConditions1)
     */
    @Deprecated
    public Matrix(int playerScore,int length, int numberWin){
        this.grid = new ArrayList<>();
        this.gameConditions = new GameConditions(playerScore, length, numberWin);
        for(int i = 0; i < length; i++){
            this.grid.add(new ArrayList<>());
            for(int j = 0; j < length; j++){
                this.grid.get(i).add(new Cell());
            }
        }
        this.setNeighbors();
    }


    /**
     * @author Jean-Baptiste
     * Constructor, initialize the grid with a specified gameConditions object
     * @param gameConditions1 is a GameCondtion object wanted to be used as initializer
     */
    public Matrix(GameConditions gameConditions1){
        this.grid = new ArrayList<>();
        this.gameConditions = gameConditions1;

        for(int i = 0; i < this.gameConditions.MatrixSize(); i++){
            this.grid.add(new ArrayList<>());
            for(int j = 0; j < this.gameConditions.MatrixSize(); j++){
                this.grid.get(i).add(new Cell());
            }
        }
        this.setNeighbors();

    }

    /*######## Getters/Setter ############*/

    /**
     * @author Jean-Baptiste
     *  set a cell in a position in the matrix
     * @param x is a the x axis
     * @param y is the y axis
     * @param cell is the cell you want to put in this slot
     */
    @Deprecated
    public void setGrid(int x, int y , Cell cell){
        this.grid.get(x).set(y, cell);
    }

    /**
     * @author Jean-Baptiste
     * set a cell in a position in the matrix
     * @param coord the coordonates where to set the cell
     * @param cell is the cell you want to put in this slot
     */
    @Deprecated
     public void setGrid(Coordonates coord, Cell cell){
        this.grid.get(coord.x()).set(coord.y(), cell);
        setNeighbors();

    }


    /** 
     * @author Jean-Baptiste
     * Gets the length of the squaer board
     * @return Length of the square board
     */
    public int getLength(){
        return this.grid.size();
    }

   /** 
    * @author Jean-Baptiste
    * Gets the height of the square board
    * @return Height of the square board
    */
    public int getHeight(){
        return this.grid.get(0).size();
    }


    @Override
    public String toString() {
        String s = "";  
        int length = this.getLength();
        int height = this.getHeight();


        for(int i = 0 ; i < length; i++) {

            s = s+ "\n";

            for(int j = 0 ; j < height ; j++){

                s = s + grid.get(i).get(j)+ " ";

            }

        }
        s = s+ "\n";
        return s;
    }


    
    
    /**
     * @author Jean-Baptiste
     * Get the cell at the specified coordinates in the grid
     * @param coord The coordonates in the grid
     * @return
     */
    public Cell getCell(Coordonates coord){
        return grid.get(coord.x()).get(coord.y());
    }
    
    
    
    /*################### Methods ################### */

    /**
     * @author Jean-Baptiste
     * Put a token in the game and check if it is won. If it is , 
     * return true.
     * @param coord The coordonates of the future token
     * @param token the token to put on the board
     * @return true if you win the game, false otherwise
     */
    public boolean putToken(Coordonates coord, Token token){
        
        
        if(isValidMove(coord)){
            if(coord.x() == getLength()-1 || coord.y() == getLength()-1){
                extendBoard();
            }
            var currentCell = this.grid.get(coord.x()).get(coord.y());
            currentCell.setToken(token);

            return currentCell.isWon(this.gameConditions.winCondition());
        }
        else{
            return false;
        }
    }

    /**
     * @author Jean-Baptiste
     * Check if the entire board is full, meaning all cells are occupied.
     * @param board The Matrix representing the game board
     */
    public boolean isBoardFull() {
        int size = this.getLength();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Coordonates coord = new Coordonates(i, j);
                if (this.getCell(coord).getColor() == Color.WHITE) {
                    return false;
                }
            }
        }
        return true;
    }

    
    /**
     * @author Jean-Baptiste
     * Check if you win the game
     * @return True if you win, false otherwise 
     * @deprecated putToken() in Cell object do the same, putToken() return
     * true if you won the game
     */
    @Deprecated
    public boolean checkIsWin(){


        for(ArrayList<Cell> c : this.grid){
            for(Cell caseCell : c){
                if(caseCell.isWon( this.gameConditions.winCondition())){
                    return true;

                }
            }
        }

        return false;
    }

    /**
     * @author Jean-Baptiste
     * Check if you win the game
     * @return True if you win, false otherwise 
     * @deprecated putToken() in Cell object do the same, putToken() return
     * true if you won the game
     */
    @Deprecated
    public boolean checkIsWin(Cell cell){
        return cell.isWon(this.gameConditions.winCondition());
    }

    /**
     * @author Jean-Baptiste
     * Sets all valid neighbors for each cell in the grid.
     * Each cell will have its surrounding cells assigned according to the 8 directions.
     */
    public final void setNeighbors() {
        int length = getLength();
        int height = getHeight();

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                Cell currentCell = this.grid.get(i).get(j);

                // check for UP
                if (i > 0) {
                    currentCell.setNeighbor(grid.get(i - 1).get(j), Direction.UP);
                }

                // check for DOWN
                if (i < length - 1) {
                    currentCell.setNeighbor(grid.get(i + 1).get(j), Direction.DOWN);
                }

                // check for LEFT
                if (j > 0) {
                    currentCell.setNeighbor(grid.get(i).get(j - 1), Direction.LEFT);
                }

                //check for  RIGHT
                if (j < length - 1) {
                    currentCell.setNeighbor(grid.get(i).get(j + 1), Direction.RIGHT);
                }

                //check for  UP_LEFT
                if (i > 0 && j > 0) {
                    currentCell.setNeighbor(grid.get(i - 1).get(j - 1), Direction.UP_LEFT);
                }

                //check for  UP_RIGHT
                if (i > 0 && j < height - 1) {
                    currentCell.setNeighbor(grid.get(i - 1).get(j + 1), Direction.UP_RIGHT);
                }

                //check for  DOWN_LEFT
                if (i < length - 1 && j > 0) {
                    currentCell.setNeighbor(grid.get(i + 1).get(j - 1), Direction.DOWN_LEFT);
                }

                //check for  DOWN_RIGHT
                if (i < length - 1 && j < height - 1) {
                    currentCell.setNeighbor(grid.get(i + 1).get(j + 1), Direction.DOWN_RIGHT);
                }
            }
        }
    }
    /**
     * @author Jean-Baptiste
     * Set the neighbors from a coordonates. Usefull for less complexity.
     * @param coord is the coordonate you want to set the neighbors
     */
    public void setNeighbors(Coordonates coord){
        var currentCell = this.getCell(coord);
        int length = getLength();
        int height = getHeight();

        // check for UP
        if (coord.x() > 0) {
            currentCell.setNeighbor(grid.get(coord.x()- 1).get(coord.y()), Direction.UP);
        }

        // check for DOWN
        if (coord.x() < length - 1) {
            currentCell.setNeighbor(grid.get(coord.x() + 1).get(coord.y()), Direction.DOWN);
        }

        // check for LEFT
        if (coord.y() > 0) {
            currentCell.setNeighbor(grid.get(coord.x()).get(coord.y() - 1), Direction.LEFT);
        }

        //check for  RIGHT
        if (coord.y() < height - 1) {
            currentCell.setNeighbor(grid.get(coord.x()).get(coord.y() + 1), Direction.RIGHT);
        }

        //check for  UP_LEFT
        if (coord.x() > 0 && coord.y() > 0) {
            currentCell.setNeighbor(grid.get(coord.x() - 1).get(coord.y() - 1), Direction.UP_LEFT);
        }

        //check for  UP_RIGHT
        if (coord.x() > 0 && coord.y() < height - 1) {
            currentCell.setNeighbor(grid.get(coord.x()- 1).get(coord.y() + 1), Direction.UP_RIGHT);
        }

        //check for  DOWN_LEFT
        if (coord.x() < length - 1 && coord.y() > 0) {
            currentCell.setNeighbor(grid.get(coord.x() + 1).get(coord.y() - 1), Direction.DOWN_LEFT);
        }

        //check for  DOWN_RIGHT
        if (coord.x() < length - 1 && coord.y() < height - 1) {
            currentCell.setNeighbor(grid.get(coord.x() + 1).get(coord.y() + 1), Direction.DOWN_RIGHT);
        }
    }

    /**
     * @author Syrine BEN HASSINE + Jean-Baptiste 
     * Says if the move is valid, can throws a NumberFormatException
     * @param coord The coordonates you want to know if 
     * @return True if the move is valid, false otherwise
     * @exception FormatEx
     * @exception IllegalArgumentException if you use a coordonates that is not possible to use
     */
    private boolean isValidMove(Coordonates coord)throws IllegalArgumentException {
        int size = this.getLength();
       

                if (coord.x() >= 0 && coord.x() < size && coord.y() >= 0 && coord.y() < size) {
                    Cell cell = this.getCell(coord);
                    if (cell.getColor() == Color.WHITE) {
                        return true;
                    }
                    throw new IllegalArgumentException("Invalid position! The cell is already used");
                } else {
                    throw new IllegalArgumentException("Invalid position! Please enter values between 1 and " + size);
                
                }

        
    }



    /**
     * @author Jean-Baptiste
     * Extends the Board in line and in column (one and one, right and down)
     */
    private void extendBoard(){
        
        int lengthBefore = getLength();
        int heightBefore = getHeight();

        //add a new Column
        for (int j = 0 ; j < lengthBefore ; j++){
            this.grid.get(j).add(new Cell());
        }

        //add a new Line
        ArrayList<Cell>newRow = new ArrayList<>(); 
        for(int i = 0 ; i < heightBefore +1; i++ ){

            newRow.add(new Cell());
        }
        this.grid.add(newRow);

        //update the neighbors
        for(int k = 0; k <getHeight(); k++){
  
            setNeighbors(new Coordonates(getLength() -1, k));
        }

        for(int k = 0; k <getLength(); k++){

            setNeighbors(new Coordonates(k, getHeight()-1));
        }
    }


}
