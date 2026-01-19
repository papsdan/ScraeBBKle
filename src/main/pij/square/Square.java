package pij.square;

import pij.tile.Tile;

/**
 * Abstract base class representing a square on the board.
 * Squares can hold tiles and may have different scoring multipliers.
 */
public abstract class Square {

    private final SquareType squareType;
    private Tile tile;

    /**
     * Constructs a new Square of the specified type - uses the Enum SquareType class.
     *
     * @param squareType the type of square (Regular,Premium letter or Premium Word)
     */
    public Square(SquareType squareType) {
        this.squareType = squareType;
    }

    /**
     * Validates that a multiplier is within the allowed range (-9 to 99).
     *
     * @param multiplier the multiplier value to validate
     * @throws IllegalArgumentException if multiplier is out of range
     */
    public void multiplierValidator(int multiplier) {
        if (multiplier < -9 || multiplier > 99) {
            throw new IllegalArgumentException("Premium square value must be between -9 and 99");
        }
    }

    /**
     * Formats the display string for a premium square.
     *
     * @param multiplier the multiplier value
     * @param suffix the character suffix ('.' for letter, '!' for word)
     * @return formatted display string
     */
    public String formatPremiumDisplay(int multiplier, char suffix) {
        String prefix = "";
        if (multiplier >= 0 && multiplier <= 9) {
            prefix = " ";
        } else {
            prefix = "";
        }
        return prefix + multiplier + suffix;
    }

    /**
     * Returns the scoring multiplier for this square.
     *
     * @return the multiplier value
     */
    public abstract int getMultiplier();

    /**
     * Returns the type of this square.
     *
     * @return the square type
     */
    public SquareType getSquareType() {
        return this.squareType;
    }

    /**
     * Abstract method to returns the display string for the square on the board.
     */
    public abstract String getDisplayString();

    /**
     * Places a tile on the square.
     *
     * @param tile the tile to place
     * @throws IllegalStateException if the square is already occupied
     */
    public void setTile(Tile tile) {
        if(isSquareOccupied()){
            throw new IllegalStateException("Square already occupied");
        }
        this.tile = tile;
    }

    /**
     * Checks if this square has a tile on it.
     *
     * @return true if occupied, false if not
     */
    public boolean isSquareOccupied() {
        return this.tile != null;
    }

    /**
     * Returns the tile on the square.
     *
     * @return the tile
     */
    public Tile getTile() {
        return this.tile;
    }
}

