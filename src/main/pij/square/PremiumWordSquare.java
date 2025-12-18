package pij.square;

import pij.tile.Tile;

public class PremiumWordSquare extends Square {
    private final int multiplier;

    public PremiumWordSquare(int multiplier) {
        super(SquareType.PREMIUM_WORD);
        multiplierValidator(multiplier);
        this.multiplier = multiplier;
    }

    @Override
    public String getDisplayString() {
        if (isSquareOccupied()) {
            Tile tile = getTile();
            return String.format("%c%-2d", Character.toUpperCase(tile.getLetter()), tile.getValue());
        }
        return formatPremiumDisplay(this.multiplier, '!');

    }


    @Override
    public int getMultiplier() {
        return this.multiplier;
    }

}
