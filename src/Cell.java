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

enum Colors {
    RED,
    BLUE,
    GREEN,
    YELLOW,
    PURPLE,
    ORANGE,
    BLACK, // black == there is no cell
    WHITE; //white ==  cell empty
}

public class Cell{

private EnumMap<Direction, Cell> directions;

private Colors color;


    /*############# Constructors ###########*/

    public Cell(){
        this.color = Colors.WHITE;
        this.directions = new EnumMap<>(Direction.class);
        
    }



    /*############# Getters/Setters ###########*/

    /**
     * Getter for colors
     * @return the color of the cell
     */
    private  Colors getColor(){
        return this.color;
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
     * set a new colors for the cell
     * @param newColor the color you want for this cell
     */
    private void setColor(Colors newColor){
        this.color = newColor;
    }
    
    
    /**
     * Get the color of a specified neighbor
     * @param direction the direction of the neighbor
     * @return the color of the neighbor
     */
    private Colors getNeighborColors(Direction direction){
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
        var s = String.format("%s", this.color);
        return s;
    }

    /*################ Methods #############*/


    /**
     * count the colors in the same direction, starting with the current cell
     * @param direction the direction you want to verrify if it is win
     * @return a boolean if it is win
     */
    private int countColors(Direction direction){

        var neighbor = this.getNeighbor(direction);
        int count = 0;

        while(neighbor != null && neighbor.getColor() == this.getColor() ) { 

            count ++;            
            neighbor.countColors(direction);
            
        }

        return (count );
    }



    /**
     * verrify if you win the game, starting with a cell
     * @param total the total number of colors in the same direction
     * @return a boolean, true if you win
     */
    public boolean isWin(int total){

        for(Direction dir : Direction.values()){
            int count = 1;
            count += countColors(dir);
            count += countColors(getOpposite(dir));

            if(count >= total){
                return true;
            }

        }
        return false;
  
    }
    
}