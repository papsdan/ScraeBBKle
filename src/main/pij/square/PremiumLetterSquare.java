package pij.square;

import pij.tile.Tile;

public class PremiumLetterSquare extends Square {
    private final int multiplier;

    public PremiumLetterSquare(int multiplier) {
        super(SquareType.PREMIUM_LETTER);
        multiplierValidator(multiplier);
        this.multiplier = multiplier;
    }

    @Override
    public String getDisplayString() {
        if (isSquareOccupied()) {
            Tile tile = getTile();
            return String.format("%c%-2d", Character.toUpperCase(tile.getLetter()), tile.getValue());
        }
        return formatPremiumDisplay(this.multiplier, '.');

    }

    @Override
    public int getMultiplier() {
        return this.multiplier;
    }

}
