package pij.square;

public class PremiumLetterSquare extends Square {
    private final int multiplier;

    public PremiumLetterSquare(int multiplier) {
        super(SquareType.PREMIUM_LETTER);
        multiplierValidator(multiplier);
        this.multiplier = multiplier;
    }

    @Override
    public String getDisplayString() {
        return formatPremiumDisplay(this.multiplier, '.');

    }

    @Override
    public int getMultiplier() {
        return this.multiplier;
    }

}
