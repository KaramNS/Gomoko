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
        
    }



    /*############# Getters/Setters ###########*/

    /**
     * Getter for colors
     * @return the color of the cell
     */
    public  Colors getColor(){
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


    @Override
    public String toString() {
        var s = String.format("%s", this.color);
        return s;
    }

    /*################ Methods #############*/

    /**
     * Verrify if it is win
     * @param direction is the direction you want to verify if it is win
     * 
     * @return true if win, else return false
     */
    public boolean  isWin(Direction direction){
        var neighbor =this.directions.get(direction);
        int counter = 0;
    
        while(neighbor.getColor() == this.getColor()){
            counter += 1;
            if(counter == 5){
                return true;
            }
            neighbor.isWin(direction);

        }
        return false;
    }
    


}