package pij.square;

public class RegularSquare extends Square {
    @Override
    public String getDisplayString() {
        return " . ";
    }

    @Override
    public int getMultiplier(){
        return 1;
    }

    @Override
    public SquareType getSquareType() {
        return SquareType.REGULAR;
    }


}
