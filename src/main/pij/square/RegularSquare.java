package pij.square;

import pij.tile.Tile;

/**
 * Represents a regular square on the board with no scoring multiplier.
 */
public class RegularSquare extends Square {
    /**
     * Constructs a new RegularSquare.
     */
    public  RegularSquare() {
        super(SquareType.REGULAR);
    }

    /**
     * Returns the display string for this square.
     * Shows " . " when empty or the tile letter and value e.g. [A1] when occupied.
     * @return the display string
     */
    @Override
    public String getDisplayString() {
        if (isSquareOccupied()) {
            Tile tile = getTile();
            return String.format("%c%-2d", tile.getLetter(), tile.getValue());
        }
        return " . ";
    }
    /**
     * Returns the multiplier for this square (always 1).
     *
     * @return 1
     */
    @Override
    public int getMultiplier(){
        return 1;
    }


}
