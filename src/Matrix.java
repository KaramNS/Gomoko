package src;

public class Matrix {
    
    private Cell[][] grid;


    /*######## Constructors ###### */

    /**
     * Default Constructor : Initialize the grid with a length of 15
     */
    public Matrix(){
        this.grid = new Cell[15][15];
        for(Cell[] e : this.grid){
            for(Cell caseCell : e){
                caseCell = new Cell();
            }
        }
    }

    /**
     * Constructor, initialize the grid with the length in the parameter
     * @param length the length of the matrix
     */
    public Matrix(int length){

        this.grid = new Cell[length][length];

        for(Cell[] e : this.grid){
            for(Cell caseCell : e){
                caseCell = new Cell();
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
                if(caseCell.isWin( 5)){
                    return true;

                }
            }
        }

        return false;
    }

}
