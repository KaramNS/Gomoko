package src;

import java.util.EnumMap;


enum Direction{
    UP,
    DOWN,
    LEFT,
    RIGHT,
    UP_LEFT,
    UP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT;

}



public class Cell{

private EnumMap<Direction, Cell> directions;

private Token token;


    /*############# Constructors ###########*/

    public Cell(){
        this.token = new Token(Color.WHITE);
        this.directions = new EnumMap<>(Direction.class);
        
    }



    /*############# Getters/Setters ###########*/

    /**
     * Getter for Color
     * @return the color of the cell
     */
    public  Color getColor(){
        return this.token.getColor();
    }
    

    /**
     * 
     * @param direction is a Direction you want to know which
     * cell is the neighbor
     * @return the cell of the direction you specified
     */
    public  Cell getNeighbor(Direction direction){
        return directions.get(direction);
    }
    
    /**
     * set a new Tokenfor the cell
     * @param newToken the Token you want for this cell
     */
    private void setToken(Token newToken){
        this.token = newToken;
    }
    
    
    /**
     * Get the color of a specified neighbor
     * @param direction the direction of the neighbor
     * @return the color of the neighbor
     */
    public Color getNeighborColor(Direction direction){
        return getNeighbor(direction).getColor();
    }
    
    
    /**
     * Get the opposite direction of the parameter
     * @param dir the direction you want to get the opposite
     * @return the opposite direction
     */
    private Direction getOpposite(Direction dir) {
        return switch (dir) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
            case UP_LEFT -> Direction.DOWN_RIGHT;
            case UP_RIGHT -> Direction.DOWN_LEFT;
            case DOWN_LEFT -> Direction.UP_RIGHT;
            case DOWN_RIGHT -> Direction.UP_LEFT;
        };
    }


    /*############ toString() ############## */

    @Override
    public String toString() {
        var s = String.format("%s", this.token);
        return s;
    }

    /*################ Methods #############*/


    /**
     * count the Color in the same direction, starting with the current cell
     * @param direction the direction you want to verrify if it is win
     * @return a boolean if it is win
     */
    private int countColor(Direction direction){

        var neighbor = this.getNeighbor(direction);
        int count = 0;

        while(neighbor != null && neighbor.getColor() == this.getColor() ) { 

            count ++;            
            neighbor.countColor(direction);
            
        }

        return (count );
    }



    /**
     * verrify if you win the game, starting with a cell
     * @param total the total number of Color in the same direction
     * @return a boolean, true if you win
     */
    public boolean isWin(int total){

        for(Direction dir : Direction.values()){
            int count = 1;
            count += countColor(dir);
            count += countColor(getOpposite(dir));

            if(count >= total){
                return true;
            }

        }
        return false;
  
    }
    
}