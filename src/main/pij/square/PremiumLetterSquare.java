package pij.square;

import pij.tile.Tile;
/**
 * Represents a premium letter square that multiplies the value of a tile placed on it.
 */
public class PremiumLetterSquare extends Square {
    private final int multiplier;


    /**
     * Constructs a new PremiumLetterSquare with the specified multiplier.
     *
     * @param multiplier the letter multiplier value (-9 to 99)
     * @throws IllegalArgumentException if multiplier is out of range
     */
    public PremiumLetterSquare(int multiplier) {
        super(SquareType.PREMIUM_LETTER);
        multiplierValidator(multiplier);
        this.multiplier = multiplier;
    }
    /**
     * Returns the display string for this square.
     * Shows the multiplier with '.' suffix when not occupied or the tile when occupied e.g. [A1].
     *
     * @return the display string
     */
    @Override
    public String getDisplayString() {
        if (isSquareOccupied()) {
            Tile tile = getTile();
            return String.format("%c%-2d", tile.getLetter(), tile.getValue());
        }
        return formatPremiumDisplay(this.multiplier, '.');

    }
    /**
     * Returns the letter multiplier for this square.
     *
     * @return the multiplier value
     */
    @Override
    public int getMultiplier() {
        return this.multiplier;
    }

}
