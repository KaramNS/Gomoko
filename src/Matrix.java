package src;

/**
 * @author Jean-Baptiste
 * @descritption This class represents the  game board
 * the number of tokens needed to win can be set in the constructor  
 * (default is 5 in a row)
 */
public class Matrix {
    
    private Cell[][] grid;
    private int numberNeededToWin = 5;


    /*######## Constructors ###### */

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
            for(int j = 0; j < length; i++){
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
            for(int j = 0; j < length; i++){
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


    
    public boolean checkIsWin(){


        for(Cell[] c : this.grid){
            for(Cell caseCell : c){
                if(caseCell.isWon( numberNeededToWin)){
                    return true;

                }
            }
        }

        return false;
    }

}
