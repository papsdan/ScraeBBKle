package pij.square;

public class PremiumWordSquare extends Square {
    private final int multiplier;

    public PremiumWordSquare(int multiplier) {
        super(SquareType.PREMIUM_WORD);
        multiplierValidator(multiplier);
        this.multiplier = multiplier;
    }

    @Override
    public String getDisplayString() {
        return formatPremiumDisplay(this.multiplier, '!');

    }


    @Override
    public int getMultiplier() {
        return this.multiplier;
    }

}
