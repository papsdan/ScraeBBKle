package pij.square;

public class Square {
    private int multiplier;
    private SquareType squareType;

    public Square(int multiplier, SquareType squareType) {
        if (multiplier < -9 || multiplier > 99) {
            throw new IllegalArgumentException("Premium square value must be between -9 and 99");
        }
        this.multiplier = multiplier;
        if (squareType == SquareType.REGULAR) {
            throw new IllegalArgumentException("Regular squares are not supposed to have multiples");
        }
        this.squareType = squareType;
    }

    public Square(SquareType squareType) {
        if (squareType != SquareType.REGULAR) {
            throw new IllegalArgumentException("Premium tiles must include multiples parameter");
        }
        this.squareType = SquareType.REGULAR;
    }

    public int getmultiplier() {
        return multiplier;
    }
    public SquareType getsquareType(){
        return this.squareType;
    }
    @Override
    public String toString() {
       String prefix = "";
        if(this.multiplier >= 0 && this.multiplier <= 9) {
           prefix = " ";
       } else {
           prefix = "";
        };
        switch (this.squareType) {
            case PREMIUM_LETTER:
                return prefix + this.multiplier;
            case PREMIUM_WORD:
                return prefix + this.multiplier + "!";
            default:
                return " . ";
        }
    }
}
