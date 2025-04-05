package src;

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
