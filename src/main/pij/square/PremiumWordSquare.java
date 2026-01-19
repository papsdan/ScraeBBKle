package pij.square;

import pij.tile.Tile;
/**
 * Represents a premium word square that multiplies the value of the entire word.
 */
public class PremiumWordSquare extends Square {
    private final int multiplier;

    /**
     * Constructs a new PremiumWordSquare with the specified multiplier.
     *
     * @param multiplier the word multiplier value (-9 to 99)
     * @throws IllegalArgumentException if multiplier is out of range
     */
    public PremiumWordSquare(int multiplier) {
        super(SquareType.PREMIUM_WORD);
        multiplierValidator(multiplier);
        this.multiplier = multiplier;
    }
    /**
     * Returns the display string for this square.
     * Shows the multiplier with '!' suffix when not occupied or the tile when occupied e.g. [A1].
     *
     * @return the display string
     */
    @Override
    public String getDisplayString() {
        if (isSquareOccupied()) {
            Tile tile = getTile();
            return String.format("%c%-2d", tile.getLetter(), tile.getValue());
        }
        return formatPremiumDisplay(this.multiplier, '!');

    }

    /**
     * Returns the word multiplier for this square.
     *
     * @return the multiplier value
     */
    @Override
    public int getMultiplier() {
        return this.multiplier;
    }

}
