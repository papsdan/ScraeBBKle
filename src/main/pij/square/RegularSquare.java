package pij.square;

public class RegularSquare extends Square {

    public  RegularSquare() {
        super(SquareType.REGULAR);
    }

    @Override
    public String getDisplayString() {
        return " . ";
    }

    @Override
    public int getMultiplier(){
        return 1;
    }


}
