package src;


enum Color {
    RED,
    BLUE,
    GREEN,
    YELLOW,
    PURPLE,
    ORANGE,
    BLACK, // black == there is no cell
    WHITE; //white ==  cell empty
}
public class Token {
    private  final Color color;



    Token(Color newColor){
        this.color = newColor;
    }


    public Color getColor(){
        return this.color;
    }
}
