package src;
/**
 * The class represents a token that you can put in the grid, 
 * it is the Atom of the game
 * @author Largeron Jean-Baptiste + Elnasory Karam
 */
public class Token 
{
    private  final Color color;

    Token(Color newColor){
        this.color = newColor;
    }

    public Color getColor(){
        return this.color;
    }

    @Override
    public String toString() {
        return ""+this.getColor();
    }
}
