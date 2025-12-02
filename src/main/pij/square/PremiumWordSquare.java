package pij.square;

public class PremiumWordSquare extends Square {
    private final int multiplier;

    public PremiumWordSquare(int multiplier) {
        if (multiplier < -9 || multiplier > 99) {
            throw new IllegalArgumentException("Premium square value must be between -9 and 99");
        }
        this.multiplier = multiplier;
    }

    @Override
    public String getDisplayString() {
        String prefix = "";
        if(this.multiplier >= 0 && this.multiplier <= 9) {
            prefix = " ";
        } else {
            prefix = "";
        }
        return prefix + this.multiplier + "!";
    }


    @Override
    public int getMultiplier() {

        return this.multiplier;
    }

    @Override
    public SquareType getSquareType(){

        return SquareType.PREMIUM_WORD;
    }

}
