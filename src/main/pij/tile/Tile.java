package pij.tile;

/**
 * Represents a single tile in the game.
 * Each tile has a letter and a point value.
 * Wildcard tiles are represented by '_' and can be assigned any letter during gameplay.
 */
public class Tile {
    private char letter;
    private final int value;
    private final boolean isWildcard;
    private boolean isWildcardAssigned;

    /**
     * Constructs a new Tile with the specified letter and value.
     * If the letter is '_', the tile is created as a wildcard.
     *
     * @param letter the letter on the tile ('_' for wildcard)
     * @param value the point value of the tile
     */
    public Tile(char letter, int value) {
        this.letter = letter;
        this.value = value;
        this.isWildcard = letter == '_';
        this.isWildcardAssigned = false;
    }
    /**
     * Returns the letter on the tile.
     *
     * @return the tile letter
     */
    public char getLetter() {

        return letter;
    }

    /**
     * Returns the point value on the tile.
     *
     * @return the tile value
     */
    public int getValue() {

        return value;
    }

    /**
     * Checks whether the tile is a wildcard
     *
     * @return true if it is a wildcard tile, false if it is not.
     */
    public boolean isWildcard() {
        return isWildcard;
    }

    /**
     * Assigns a letter to a wildcard tile.
     * It can only be used on wildcard tiles. The letter
     * can not be reassigned once the is placed on the board - then the assigned letter will be permanent.
     *
     * @param letter the letter to assign to the wildcard
     * @throws RuntimeException if wildcard letter already set for the tile
     */
    public void setWildcardLetter(char letter) {
        if (this.isWildcard && !this.isWildcardAssigned) {
            this.letter = letter;
            this.isWildcardAssigned = true;
        } else {
            throw new RuntimeException("Wildcard letter already set");
        }
    }
    /**
     * Returns a string representation of the tile in format [letter value] e.g. [A1].
     *
     * @return string representation of the tile
     */
    @Override
    public String toString() {
        return "[" + letter + value + "]";
    }
    
}
