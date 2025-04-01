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

    Cell(){
        this.color = Colors.WHITE;
    }



    /*############# Getters/Setters ###########*/

        public Colors getColor(){
        return this.color;
    }
        public Cell getNeighbor(Direction direction){
        return directions.get(direction);
    }

    public void setColor(Colors newColor){
        this.color = newColor;
    }

    @Override
    public String toString() {
        var s = String.format("Cell color: %s", this.colo)
        return s;
    }


    
}