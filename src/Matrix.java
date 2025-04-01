package src;

public class Matrix {
    
    private Cell[][] table;



    /*######## Constructors ###### */

    public Matrix(){
        this.table = new Cell[15][15];
    }


    /*######## Getters/Setter */

    /**
     *  set a cell in a position in the matrix
     * @param x is a the x axis
     * @param y is the y axis
     * @param cell is the cell you want to put in this slot
     */
    public void setTable(int x, int y , Cell cell){
        this.table[x][y] = cell;
    }



 
    @Override
    public String toString() {
        String s = "";
        int length = this.table.length;


        for(int i = 0 ; i < length; i++) {

            s = s+ "\n";

            for(int j = 0 ; j < length ; j++){

                s = s + table[i][j] + " ";

            }

            s = s+ "\n";
        }
        return s;
    }


    

}
