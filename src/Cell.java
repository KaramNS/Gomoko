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
    WHITE;
}

public class Cell{

private EnumMap<Direction, Cell> directions;

private Colors color;


    /*############# Constructors ###########*/

    public Cell(){
        this.color = Colors.WHITE;
    }



    /*############# Getters/Setters ###########*/

    /**
     * Getter for colors
     * @return the color of the cell
     */
    public Colors getColor(){
        return this.color;
    }


    /**
     * 
     * @param direction is a Direction you want to know which
     * cell is the neighbor
     * @return the cell of the direction you specified
     */
    public Cell getNeighbor(Direction direction){
        return directions.get(direction);
    }

    /**
     * set a new colors for the cell
     * @param newColor the color you want for this cell
     */
    public void setColor(Colors newColor){
        this.color = newColor;
    }


    /**
     * Get the color of a specified neighbor
     * @param direction the direction of the neighbor
     * @return the color of the neighbor
     */
    public Colors getNeighbColors(Direction direction){
        return getNeighbor(direction).getColor();
    }


    @Override
    public String toString() {
        var s = String.format("%s", this.color);
        return s;
    }

    /*################ Methods #############*/

    


}